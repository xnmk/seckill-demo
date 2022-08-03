package me.xnmk.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xnmk.seckill.exception.GlobalException;
import me.xnmk.seckill.mapper.UserMapper;
import me.xnmk.seckill.pojo.User;
import me.xnmk.seckill.service.IUserService;
import me.xnmk.seckill.utils.CookieUtil;
import me.xnmk.seckill.utils.MD5Util;
import me.xnmk.seckill.utils.UUIDUtil;
import me.xnmk.seckill.vo.LoginVo;
import me.xnmk.seckill.vo.RespBean;
import me.xnmk.seckill.vo.RespBeanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xnmk
 * @since 2022-07-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        Long mobile = Long.valueOf(loginVo.getMobile());
        String password = loginVo.getPassword();
        // 检验
        // if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
        //     return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        // }
        // if (!ValidatorUtil.isMobiile(mobile)) {
        //     return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        // }

        // 用户是否存在
        User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        // 校验密码
        if (!MD5Util.fromPassToDBPass(loginVo.getPassword(), user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        // 生成Cookie
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:" + ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);

        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) return null;
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) CookieUtil.setCookie(request, response, "userTicket", userTicket);
        return user;
    }
}
