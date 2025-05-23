package com.example.plusteamproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonSerializer = createJsonSerializer();

        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        return template;
    }

    //Java의 LocalDateTime을 처리하기 위해 JavaTimeModule을 등록한 ObjectMapper를 사용하여 JSON 직렬화기 생성
    private GenericJackson2JsonRedisSerializer createJsonSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }


    //spring boot 2.x 버전 이상은 자동으로 주입됨
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        return redisTemplate;}


}
