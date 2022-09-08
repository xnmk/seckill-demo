package me.xnmk.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xnmk.seckill.pojo.SeckillOrder;
import me.xnmk.seckill.pojo.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获得秒杀结果
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return 订单id
     */
    Long getResult(User user, Long goodsId);
}
