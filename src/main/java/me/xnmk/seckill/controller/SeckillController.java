package me.xnmk.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.util.concurrent.RateLimiter;
import me.xnmk.seckill.pojo.Order;
import me.xnmk.seckill.pojo.SeckillMessage;
import me.xnmk.seckill.pojo.SeckillOrder;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.rabbitmq.MQSender;
import me.xnmk.seckill.service.IGoodsService;
import me.xnmk.seckill.service.IOrderService;
import me.xnmk.seckill.service.ISeckillOrderService;
import me.xnmk.seckill.vo.GoodsVo;
import me.xnmk.seckill.vo.RespBean;
import me.xnmk.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author:xnmk_zhan
 * @create:2022-07-18 16:20
 * @Description: SeckillController
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisScript<Long> script;
    @Autowired
    private MQSender sender;

    private Map<Long, Boolean> emptyStockMap = new HashMap<>();

    //每秒放行10个请求
    RateLimiter rateLimiter = RateLimiter.create(2000);

    /**
     * 秒杀
     *
     * @param path
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/{path}/doSeckill")
    @ResponseBody
    public RespBean doSeckill(@PathVariable String path, User user, Long goodsId) {
        if (user == null) return RespBean.error(RespBeanEnum.USER_TIME_OUT);

        // 非阻塞式获得令牌
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            return RespBean.error(RespBeanEnum.REQUEST_FAST);
        }

        // 校验接口
        boolean check = orderService.checkPath(user, goodsId, path);
        if (!check) {
            return RespBean.error(RespBeanEnum.PATH_ERROR);
        }

        // 判断是否重复抢购（分布式锁）
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent("lock:" + goodsId + ":" + user.getId(), user.getId(), 120, TimeUnit.SECONDS);
        if (!isLock) return RespBean.error(RespBeanEnum.HAS_SECKILL);

        // 通过内存标记减少Redis访问
        if (emptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 预减库存
        Long stock = (Long) redisTemplate.execute(script, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
        if (stock < 0) {
            emptyStockMap.put(goodsId, true);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 下单
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        sender.sendSeckillMessage(JSON.toJSONString(seckillMessage));

        return RespBean.success(0);
    }

    /**
     * 获取秒杀结果
     *
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/result")
    @ResponseBody
    public RespBean getResult(User user, Long goodsId) {
        if (user == null) return RespBean.error(RespBeanEnum.USER_TIME_OUT);
        Long OrderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(OrderId);
    }

    /**
     * 获取秒杀地址
     *
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/path")
    @ResponseBody
    public RespBean getPath(User user, Long goodsId, HttpServletRequest request) {
        if (user == null) return RespBean.error(RespBeanEnum.USER_TIME_OUT);
        String uri = request.getRequestURI();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer count = (Integer) valueOperations.get(uri + ":" + user.getId());
        // 单用户限流
        if (count == null) {
            valueOperations.set(uri + ":" + user.getId(), 1, 5, TimeUnit.SECONDS);
        } else if (count < 5) {
            valueOperations.increment(uri + ":" + user.getId());
        } else {
            return RespBean.error(RespBeanEnum.REQUEST_FAST);
        }
        String str = orderService.createPath(user, goodsId);
        return RespBean.success(str);
    }

    /**
     * 系统初始化时，将商品库存加载入 Redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.findGoodVo();
        if (CollectionUtils.isEmpty(goodsVoList)) {
            return;
        }
        goodsVoList.forEach(goodsVo -> {
            // 添加内存标记
            emptyStockMap.put(Long.valueOf(goodsVo.getId()), false);
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
        });
    }
}
