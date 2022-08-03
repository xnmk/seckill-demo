package me.xnmk.seckill.controller;


import me.xnmk.seckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xnmk
 * @since 2022-07-16
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/info")
    @ResponseBody
    public User info(User user){
        return user;
    }
}
