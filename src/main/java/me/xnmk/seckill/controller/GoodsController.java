package me.xnmk.seckill.controller;

import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.service.IGoodsService;
import me.xnmk.seckill.service.IUserService;
import me.xnmk.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author:xnmk_zhan
 * @create:2022-07-17 16:29
 * @Description: Goods Controller
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;

    /**
     * 跳转商品页
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodVo());
        return "goodsList";
    }

    /**
     * 跳转商品详情页
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable("goodsId") Long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        // 秒杀状态
        int secKillStatus = 0;
        // 秒杀倒计时
        int remainSeconds = 0;
        if (nowDate.before(startDate)) {
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }
}
