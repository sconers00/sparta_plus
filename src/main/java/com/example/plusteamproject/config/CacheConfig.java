package com.example.plusteamproject.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    // @Bean
    // public CaffeineCacheManager cacheManager() {
    //     CaffeineCacheManager cacheManager = new CaffeineCacheManager("searchCache");
    //     cacheManager.setCaffeine(caffeineCacheBuilder());
    //     return cacheManager;
    // }

    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(1000);
    }
}
