package com.fan;

import com.fan.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedissonApplicationTests {


    @Autowired
    RedisService redisService;


    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    redisTemplate.opsForValue().set("stockCount",5000);

    }

}
