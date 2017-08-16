package com.github.louchen.zoom.secruity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenStore {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${jwt.expiration}")
    private Long expiration;

    private static final String PREFIX = "JWT-TOKEN-";

    public void set(String id) {
        stringRedisTemplate.opsForValue().set(PREFIX + id, id, expiration, TimeUnit.SECONDS);
    }

    public String get(String id) {
        return stringRedisTemplate.opsForValue().get(PREFIX + id);
    }

}
