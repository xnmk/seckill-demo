package me.xnmk.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xnmk.seckill.mapper.GoodsMapper;
import me.xnmk.seckill.pojo.Goods;
import me.xnmk.seckill.service.IGoodsService;
import me.xnmk.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-17
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
