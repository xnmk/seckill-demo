package me.xnmk.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:xnmk_zhan
 * @create:2022-08-04 16:19
 * @Description: 秒杀信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {

    /** 用户信息 */
    private User user;

    /** 商品id */
    private Long goodsId;
}
