package me.xnmk.seckill.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:xnmk_zhan
 * @create:2022-07-16 19:29
 * @Description: ValidatorUtil
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");
    public static boolean isMobiile(String mobile) {
        if(StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
