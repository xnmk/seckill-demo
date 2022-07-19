package me.xnmk.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xnmk.seckill.pojo.Goods;
import me.xnmk.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获得商品列表
     *
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获得商品详情
     *
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
