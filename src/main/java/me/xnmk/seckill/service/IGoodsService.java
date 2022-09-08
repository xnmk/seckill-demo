package me.xnmk.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xnmk.seckill.pojo.Goods;
import me.xnmk.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     *
     * @return
     */
    List<GoodsVo> findGoodVo();

    /**
     * 获得商品详情
     *
     * @param goodsId 商品id
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
