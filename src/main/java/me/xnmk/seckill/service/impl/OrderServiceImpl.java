package me.xnmk.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xnmk.seckill.exception.GlobalException;
import me.xnmk.seckill.mapper.OrderMapper;
import me.xnmk.seckill.pojo.Order;
import me.xnmk.seckill.pojo.SeckillGoods;
import me.xnmk.seckill.pojo.SeckillOrder;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.service.IGoodsService;
import me.xnmk.seckill.service.IOrderService;
import me.xnmk.seckill.service.ISeckillGoodsService;
import me.xnmk.seckill.service.ISeckillOrderService;
import me.xnmk.seckill.vo.GoodsVo;
import me.xnmk.seckill.vo.OrderDetailVo;
import me.xnmk.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public OrderDetailVo getDetail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(Long.valueOf(order.getGoodsId()));
        OrderDetailVo orderDetailVo = new OrderDetailVo(order, goodsVo);
        return orderDetailVo;
    }

    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goods) {
        // // 获得商品减存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>()
                .eq("goods_id", goods.getId()));
        // // 库存小于1直接返回
        // if (seckillGoods.getStockCount() < 1) {
        //     return null;
        // }
        // seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // 秒杀：解决多卖问题是交由mysql底层update语句会对该行添加互斥锁实现
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = stock_count - 1")
                .eq("goods_id", goods.getId())
                .gt("stock_count", 0));
        // 没成功则直接返回
        if (!result) return null;

        // 生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliverAddrId(0);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        // 生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        // 将秒杀订单加入到 redis
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), seckillOrder);

        return order;
    }
}
