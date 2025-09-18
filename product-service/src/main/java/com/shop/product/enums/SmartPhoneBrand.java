package com.shop.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmartPhoneBrand {
  APPLE("Apple"),
  SAMSUNG("Samsung"),
  XIAOMI("Xiaomi"),
  OPPO("Oppo"),
  VIVO("Vivo"),
  REALME("Realme"),
  ONEPLUS("OnePlus"),
  GOOGLE("Google"),
  HUAWEI("Huawei"),
  MOTOROLA("Motorola"),
  NOKIA("Nokia"),
  SONY("Sony"),
  LG("LG"),
  ASUS("Asus"),
  LENOVO("Lenovo");
  ;
  private final String name;
}
