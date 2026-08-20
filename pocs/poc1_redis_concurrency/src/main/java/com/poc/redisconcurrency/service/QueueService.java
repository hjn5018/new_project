package com.poc.redisconcurrency.service;

import java.time.Duration;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String QUEUE_KEY_PREFIX = "queue:";
    private static final String TOKEN_KEY_PREFIX = "token:";

    /**
     * 1. 대기열 진입 (ZADD: 현재 타임스탬프를 Score로 하여 선착순 줄세우기)
     */
    public Long enterQueue(String eventId, String userId) {
        String queueKey = QUEUE_KEY_PREFIX + eventId;
        double timestamp = (double) System.currentTimeMillis();

        // Redis ZSET에 추가 (Key, Value, Score)
        redisTemplate.opsForZSet().add(queueKey, userId, timestamp);

        return getQueueRank(eventId, userId);
    }

    /**
     * 2. 내 대기 순번 조회 (ZRANK: 0등부터 시작하므로 +1)
     */
    public Long getQueueRank(String eventId, String userId) {
        String queueKey = QUEUE_KEY_PREFIX + eventId;
        Long rank = redisTemplate.opsForZSet().rank(queueKey, userId);

        if (rank == null) {
            return null; // 대기열에 없음 (이미 통과했거나 나감)
        }
        return rank + 1; // 1등부터 표시
    }

    /**
     * 3. 대기열 상위 N명을 활성 토큰(입장권)으로 승격 (1초마다 워커가 호출)
     */
    public void allowUsers(String eventId, long count) {
        String queueKey = QUEUE_KEY_PREFIX + eventId;

        // 대기열 상위 count명 꺼내기
        Set<Object> allowedUsers = redisTemplate.opsForZSet().range(queueKey, 0, count - 1);
        if (allowedUsers == null || allowedUsers.isEmpty()) {
            return;
        }

        for (Object user : allowedUsers) {
            String userId = (String) user;
            String tokenKey = TOKEN_KEY_PREFIX + eventId + ":" + userId;

            // 5분 유효기간(TTL)의 활성 토큰 발급
            redisTemplate.opsForValue().set(tokenKey, "ACTIVE", Duration.ofMinutes(5));

            // 대기열에서 제거
            redisTemplate.opsForZSet().remove(queueKey, userId);
        }
    }

    /**
     * 4. 유효한 활성 입장권(토큰)을 가졌는지 검증
     */
    public boolean isValidToken(String eventId, String userId) {
        String tokenKey = TOKEN_KEY_PREFIX + eventId + ":" + userId;
        Boolean exists = redisTemplate.hasKey(tokenKey);
        return Boolean.TRUE.equals(exists);
    }
}
