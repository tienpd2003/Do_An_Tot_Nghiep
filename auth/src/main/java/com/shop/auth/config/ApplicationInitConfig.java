package com.shop.auth.config;

import com.shop.auth.constant.PredefinedRole;
import com.shop.auth.entity.Role;
import com.shop.auth.entity.User;
import com.shop.auth.repository.RoleRepository;
import com.shop.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

  private final PasswordEncoder passwordEncoder;

  @Bean
  @ConditionalOnProperty(
          prefix = "spring",
          value = "datasource.driver-class-name",
          havingValue = "org.postgresql.Driver")
  @Transactional
  ApplicationRunner applicationRunner(UserRepository userRepository,
                                      RoleRepository roleRepository) {
    log.info("Initializing application.....");
    return args -> {
      Role userRole = roleRepository.findByRoleName(PredefinedRole.USER)
              .orElseGet(() -> roleRepository.save(Role.builder()
                      .roleName(PredefinedRole.USER)
                      .build()));
      Role adminRole = roleRepository.findByRoleName(PredefinedRole.ADMIN)
              .orElseGet(() -> roleRepository.save(Role.builder()
                      .roleName(PredefinedRole.ADMIN)
                      .build()));

      if (userRepository.findByUsername("customer").isEmpty()) {
        User customer = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("123456"))
                .fullName("Customer User")
                .phone("0123456789")
                .email("vuongnm.contact@gmail.com")
                .address("Hungyen, Vietnam")
                .roles(Collections.singleton(userRole))
                .build();
        userRepository.save(customer);
      }

      if (userRepository.findByUsername("admin").isEmpty()) {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .fullName("Admin User")
                .phone("0123456789")
                .email("admin.contact@gmail.com")
                .address("Hanoi, Vietnam")
                .roles(Collections.singleton(adminRole))
                .build();
        userRepository.save(admin);
      }
      log.info("Application initialization completed .....");
    };
  }
}
