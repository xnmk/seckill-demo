package me.xnmk.seckill;

import me.xnmk.seckill.vo.RespBean;
import me.xnmk.seckill.vo.RespBeanEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SeckillDemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DefaultRedisScript<Boolean> lockScript;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().setIfAbsent("123", "ASDADASD", 3600, TimeUnit.SECONDS);
    }

    @Test
    void testReleaseLock() {
        redisTemplate.execute(lockScript, Arrays.asList("123"), "ASDADASD");
    }

}
