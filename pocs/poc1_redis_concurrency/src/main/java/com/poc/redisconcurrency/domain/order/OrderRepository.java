package com.poc.redisconcurrency.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 특정 상품에 대해 생성된 총 주문 건수 조회
     * (동시성 테스트 검증 시 사용: SELECT COUNT(*) FROM orders WHERE product_id = ?)
     */
    long countByProductId(Long productId);
}
