package com.poc.redisconcurrency.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int stock; // 잔여 재고 수량

    private long price;

    public Product(String name, int stock, long price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    /**
     * 재고 차감 비즈니스 로직 (객체지향 캡슐화)
     */
    public void decreaseStock(int quantity) {
        if (this.stock - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다. (현재 재고: " + this.stock + ")");
        }
        this.stock -= quantity;
    }
}
