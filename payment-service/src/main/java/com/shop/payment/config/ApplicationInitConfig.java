package com.shop.payment.config;

import com.shop.payment.entity.UserBalance;
import com.shop.payment.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
  @Bean
  @ConditionalOnProperty(
          prefix = "spring.data.mongodb",
          value = "uri")
  @Transactional
  ApplicationRunner applicationRunner(UserBalanceRepository userBalanceRepository) {
    log.info("Initializing application.....");
    return args -> {

      if (userBalanceRepository.findByUserId(1L).isEmpty()) {
        UserBalance userBalance = UserBalance.builder()
                .userId(1L)
                .balance(20000L)
                .build();
        userBalanceRepository.save(userBalance);
      }

      if (userBalanceRepository.findByUserId(2L).isEmpty()) {
        UserBalance userBalance = UserBalance.builder()
                .userId(2L)
                .balance(20000L)
                .build();
        userBalanceRepository.save(userBalance);
      }
      log.info("Application initialization completed .....");
    };
  }
}
