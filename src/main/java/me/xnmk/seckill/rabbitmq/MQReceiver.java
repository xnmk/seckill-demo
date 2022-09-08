package me.xnmk.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.xnmk.seckill.pojo.SeckillMessage;
import me.xnmk.seckill.pojo.SeckillOrder;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.service.IGoodsService;
import me.xnmk.seckill.service.IOrderService;
import me.xnmk.seckill.utils.RedisKeyUtil;
import me.xnmk.seckill.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author:xnmk_zhan
 * @create:2022-08-04 15:30
 * @Description: 消息消费者
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = "seckillQueue")
    public void receiveSeckillMessage(String message) {
        log.info("接收到的消息：" + message);
        SeckillMessage seckillMessage = JSON.parseObject(message, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // 再次判断库存
        if (goodsVo.getStockCount() < 1) {
            return;
        }
        // 再次判断是否重复秒杀
        String orderKey = RedisKeyUtil.getOrderKey(user.getId(), goodsId.intValue());
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get(orderKey);
        if (seckillOrder != null) {
            return;
        }
        // 下单
        orderService.seckill(user, goodsVo);
    }
}
