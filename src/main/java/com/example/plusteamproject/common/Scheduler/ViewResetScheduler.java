package com.example.plusteamproject.common.Scheduler;

import com.example.plusteamproject.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ViewResetScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;

    //캐시 초기화 시 캐시 데이터의 조회수가 각자 product의 totalview 로 중첩 저장
    @Scheduled(cron = "0 * * * * ?") // 임시 1분마다 캐시 초기화
    @Transactional
    public void resetViewCounts() {
        Set<Object> productIds = redisTemplate.opsForZSet().range("viewCount", 0, -1);
        if (productIds != null) {
            for (Object productIdObj : productIds) {
                try {
                    Long productId = Long.valueOf(productIdObj.toString());
                    Double score = redisTemplate.opsForZSet().score("viewCount", productIdObj);
                    if (score != null && score > 0) {
                        productRepository.findById(productId).ifPresent(product -> {
                            product.incrementTotalViewByRedis(score.longValue());
                            productRepository.save(product);
                        });
                    }
                } catch (NumberFormatException e) {
                    System.err.println("해당제품의 데이터가 삭제되었습니다 .");
                }
            }
        }

        redisTemplate.delete("viewCount");
        redisTemplate.delete(redisTemplate.keys("viewId:*"));
        redisTemplate.delete(redisTemplate.keys("product:*"));
    }
}
