package me.xnmk.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.vo.LoginVo;
import me.xnmk.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-16
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     *
     * @param loginVo  登录信息
     * @param request  请求
     * @param response 响应
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据Cookie获取用户
     *
     * @param userTicket 用户凭证
     * @return 用户信息
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
