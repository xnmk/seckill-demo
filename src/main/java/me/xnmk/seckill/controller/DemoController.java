package me.xnmk.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:xnmk_zhan
 * @create:2022-07-15 15:27
 * @Description: 测试
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "xnmk");
        return "hello";
    }

    @ResponseBody
    @GetMapping("/redisTest")
    public String testRedis() {
        //设置值到redis
        redisTemplate.opsForValue().set("name", "lucy");
        //从redis获取值
        String name = (String) redisTemplate.opsForValue().get("name");
        // System.out.println(name);
        return name;

    }
}
