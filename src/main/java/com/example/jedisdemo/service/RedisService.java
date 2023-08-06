package com.example.jedisdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    public void save(String key, String id, Object value) {
        redisTemplate.opsForHash().put(key, id, value);
    }

    public <T> T getValue(String key, String id, Class<T> tClass) {
        return (T) redisTemplate.opsForHash().get(key, id);
    }

}
