package com.shop.order.enums;

import lombok.Getter;

@Getter
public enum StatusStock {
    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE"),
    OUT_OF_STOCK("OUT_OF_STOCK");

    private final String status;

    StatusStock(String status) {
        this.status = status;
    }
}
