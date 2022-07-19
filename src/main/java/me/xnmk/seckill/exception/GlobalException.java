package me.xnmk.seckill.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xnmk.seckill.vo.RespBeanEnum;

/**
 * @author:xnmk_zhan
 * @create:2022-07-17 15:50
 * @Description: 全局异常
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;
}
