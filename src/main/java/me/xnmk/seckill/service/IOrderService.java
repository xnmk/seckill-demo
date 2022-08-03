package me.xnmk.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xnmk.seckill.pojo.Order;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.vo.GoodsVo;
import me.xnmk.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     *
     * @param user
     * @param goods
     * @return
     */
    Order seckill(User user, GoodsVo goods);

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    OrderDetailVo getDetail(Long orderId);
}
