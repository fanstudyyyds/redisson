package com.fan.controller;

import com.fan.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RedisController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/add")
    public String shou(String name) {
        return redisService.add(name);
    }
}
