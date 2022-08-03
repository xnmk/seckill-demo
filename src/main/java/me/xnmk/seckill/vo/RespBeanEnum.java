package me.xnmk.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author:xnmk_zhan
 * @create:2022-07-16 17:38
 * @Description: ResponseBean Enum
 */
public enum RespBeanEnum {

    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    LOGIN_ERROR(500210,"帐号或密码错误"),
    MOBILE_ERROR(500310,"手机格式错误"),
    BIND_ERROR(500410,"参数校验异常"),
    EMPTY_STOCK(500510, "库存不足"),
    HAS_SECKILL(500610, "每个人限购一次"),
    PASSWORD_UPDATE(500710,"更新密码失败"),
    USER_TIME_OUT(500810,"用户登录过期"),
    PATH_ERROR(500910, "路径错误"),
    ERROR_CAPTCHA(500110,"验证码错误"),
    ORDER_NOT_EXIST(500300,"订单消息不存在"),
    REQUEST_FAST(501000,"请求频繁");

    private final Integer code;
    private final String message;

    RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return "RespBeanEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
