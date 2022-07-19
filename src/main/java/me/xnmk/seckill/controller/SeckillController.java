package me.xnmk.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.xnmk.seckill.pojo.Order;
import me.xnmk.seckill.pojo.SeckillOrder;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.service.IGoodsService;
import me.xnmk.seckill.service.IOrderService;
import me.xnmk.seckill.service.ISeckillOrderService;
import me.xnmk.seckill.vo.GoodsVo;
import me.xnmk.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:xnmk_zhan
 * @create:2022-07-18 16:20
 * @Description: SeckillController
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISeckillOrderService seckillOrderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if (user == null) return "login";
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断库存
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        // 判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.HAS_SECKILL.getMessage());
            return "secKillFail";
        }
        Order order = orderService.seckill(user,goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }
}
