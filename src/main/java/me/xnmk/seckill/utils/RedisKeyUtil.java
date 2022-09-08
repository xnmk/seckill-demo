package me.xnmk.seckill.utils;

/**
 * @author:xnmk_zhan
 * @create:2022-09-08 22:21
 * @Description: RedisKey 生成类
 */
public class RedisKeyUtil {

    /**
     * 分隔符
     */
    private static final String SPLIT = ":";
    /**
     * 用户信息
     */
    private static final String PREFIX_USER = "user";
    /**
     * 分布式锁
     */
    private static final String PREFIX_LOCK = "lock";
    /**
     * 限流
     */
    private static final String PREFIX_LIMIT = "limit";
    /**
     * 秒杀商品预存
     */
    private static final String PREFIX_SECKILLGOODS = "seckillGoods";
    /**
     * 订单
     */
    private static final String PREFIX_ORDER = "order";

    /**
     * 用户信息key
     *
     * @param ticket 凭证
     * @return
     */
    public static String getUserKey(String ticket) {
        return PREFIX_USER + SPLIT + ticket;
    }

    /**
     * 分布式锁key
     *
     * @param goodsId 商品id
     * @param userId  用户id
     * @return
     */
    public static String getLockKey(Long goodsId, Long userId) {
        return PREFIX_LOCK + SPLIT + goodsId + SPLIT + userId;
    }

    /**
     * 接口限流key
     *
     * @param uri    接口路径
     * @param userId 用户id
     * @return
     */
    public static String getLimitKey(String uri, Long userId) {
        return PREFIX_LIMIT + SPLIT + uri + SPLIT + userId;
    }

    /**
     * 秒杀商品预存key
     *
     * @param goodsId 商品id
     * @return
     */
    public static String getSeckillGoodsKey(int goodsId) {
        return PREFIX_SECKILLGOODS + SPLIT + goodsId;
    }

    /**
     * 商品信息key
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return
     */
    public static String getOrderKey(Long userId, int goodsId) {
        return PREFIX_ORDER + SPLIT + userId + SPLIT + goodsId;
    }
}
