package com.shop.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("CREATED"),
    CONFIRMED("CONFIRMED"),
    PROCESSING("PROCESSING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

}
