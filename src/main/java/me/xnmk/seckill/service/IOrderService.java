package me.xnmk.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xnmk.seckill.pojo.Order;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.vo.GoodsVo;
import me.xnmk.seckill.vo.OrderDetailVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     *
     * @param user  用户
     * @param goods 商品
     * @return
     */
    Order seckill(User user, GoodsVo goods);

    /**
     * 订单详情
     *
     * @param orderId 订单id
     * @return
     */
    OrderDetailVo getDetail(Long orderId);

    /**
     * 获取秒杀地址
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return
     */
    String createPath(User user, Long goodsId);

    /**
     * 校验秒杀地址
     *
     * @param user    用户
     * @param goodsId 商品id
     * @param path    地址
     * @return
     */
    boolean checkPath(User user, Long goodsId, String path);
}
