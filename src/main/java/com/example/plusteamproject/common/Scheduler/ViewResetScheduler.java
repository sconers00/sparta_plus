package com.example.plusteamproject.common.Scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ViewResetScheduler {

    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void resetViewCounts() {
        redisTemplate.delete("viewCount");

        Set<String> keys = redisTemplate.keys("viewId:*");
        Set<String> products = redisTemplate.keys("product:*");
        if (!keys.isEmpty() || !products.isEmpty()) {
            redisTemplate.delete(keys);
            redisTemplate.delete(products);
        }

    }
}

