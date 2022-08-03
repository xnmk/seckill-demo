package me.xnmk.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xnmk.seckill.pojo.Order;

/**
 * @author:xnmk_zhan
 * @create:2022-08-03 17:07
 * @Description: 订单详情返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {

    private Order order;

    private GoodsVo goodsVo;
}
