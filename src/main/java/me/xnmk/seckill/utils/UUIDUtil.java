package me.xnmk.seckill.utils;

import java.util.UUID;

/**
 * @author:xnmk_zhan
 * @create:2022-07-17 16:23
 * @Description: UUID Util
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
