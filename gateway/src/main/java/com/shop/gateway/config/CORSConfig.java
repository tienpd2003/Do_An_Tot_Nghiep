package com.shop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CORSConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true); // nếu cần cookie/token
    corsConfig.addAllowedOriginPattern("*"); // dùng OriginPattern thay vì AllowedOrigins
    corsConfig.addAllowedHeader("*");
    corsConfig.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // áp dụng cho tất cả path
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}