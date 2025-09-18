package com.shop.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryName {
  SMARTPHONE("SMARTPHONE"),
  LAPTOP("LAPTOP"),
  TABLET("TABLET"),
  ACCESSORY("ACCESSORY"),;

  private final String name;
}
