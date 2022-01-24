package com.fan.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    public  String add(String name) {

        String lockKey = "stockKey";
        String id = UUID.randomUUID().toString();
        try {
            // 这里默认设置超时时间为30秒
            boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, id, 30, TimeUnit.SECONDS);
            if (!result) {
                log.info("errorCode {}",name);
                return "errorCode";

            }
            // 从redis中获取库存数量
            int stock = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get("stockCount") + ""));
            if (stock > 0) {
                // 减库存
                int restStock = stock - 1;
                // 剩余库存再重新设置到redis中
                redisTemplate.opsForValue().set("stockCount", String.valueOf(restStock));
                log.info("扣减成功，剩余库存：{},抢到的人是{}", restStock,name);
            } else {
                log.info("库存不足，扣减失败。");
            }
        } finally {
            if (id.contentEquals(Objects.requireNonNull(redisTemplate.opsForValue().get(lockKey) + ""))) {
                redisTemplate.delete(lockKey);
            }

        }
        return redisTemplate.opsForValue().get("stockCount") + "";
    }

    public  String addsynchronized(String name) {


        try {

            // 从redis中获取库存数量
            int stock = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get("stockCount") + ""));
            if (stock > 0) {
                // 减库存
                int restStock = stock - 1;
                // 剩余库存再重新设置到redis中
                redisTemplate.opsForValue().set("stockCount", String.valueOf(restStock));
                log.info("扣减成功，剩余库存：{},抢到的人是{}", restStock,name);
            } else {
                log.info("库存不足，扣减失败。");
            }
        } finally {


        }
        return redisTemplate.opsForValue().get("stockCount") + "";
    }

}