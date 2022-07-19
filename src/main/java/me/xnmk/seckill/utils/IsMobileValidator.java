package me.xnmk.seckill.utils;

import me.xnmk.seckill.vaildator.IsMobile;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author:xnmk_zhan
 * @create:2022-07-17 15:42
 * @Description: 手机号格式校验规则
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean require = false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        require = constraintAnnotation.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(require) {
            return ValidatorUtil.isMobiile(value);
        } else {
            if(StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidatorUtil.isMobiile(value);
            }
        }
    }
}
