package com.poc.redisconcurrency.domain.order;

public enum OrderStatus {
    PENDING,    // 주문 대기
    COMPLETED,  // 주문 및 재고 차감 완료
    FAILED,     // 주문 실패 (품절 등)
    CANCELLED   // 주문 취소
}
