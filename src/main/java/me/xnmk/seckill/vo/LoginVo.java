package me.xnmk.seckill.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import me.xnmk.seckill.vaildator.IsMobile;
import org.hibernate.validator.constraints.Length;

/**
 * @author:xnmk_zhan
 * @create:2022-07-16 19:12
 * @Description: LoginVo
 */
@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String  mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
