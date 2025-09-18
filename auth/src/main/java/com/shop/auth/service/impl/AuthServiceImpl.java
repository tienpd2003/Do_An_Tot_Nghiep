package com.shop.auth.service.impl;

import com.shop.auth.config.security.JwtUtils;
import com.shop.auth.dto.request.LoginReq;
import com.shop.auth.dto.request.RegisterReq;
import com.shop.auth.dto.request.VerifyReq;
import com.shop.auth.dto.response.LoginResponse;
import com.shop.auth.dto.response.VerifyResponse;
import com.shop.auth.entity.Role;
import com.shop.auth.entity.User;
import com.shop.auth.enums.AuthErrorCode;
import com.shop.auth.repository.RoleRepository;
import com.shop.auth.repository.UserRepository;
import com.shop.auth.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vuongdev.common.dto.response.IDResponse;
import org.vuongdev.common.exception.AppException;
import org.vuongdev.common.exception.ErrorCodeUtils;
import org.vuongdev.common.utils.JwtTokenUtils;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
  @Value("${jwt.secretKey}")
  private String SECRET_KEY;

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  public LoginResponse login(LoginReq loginReq) {
    Optional<User> userOptional = userRepository.findByUsername(loginReq.getUsername());
    if (userOptional.isEmpty()) {
      throw new AppException(AuthErrorCode.USERNAME_NOT_EXISTS);
    }
    try {
      authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
    } catch (BadCredentialsException ex) {
      // wrong password
      throw new AppException(AuthErrorCode.LOGIN_FAILED);
    }
    User user = userOptional.get();
    String jwtToken = jwtUtils.generateToken(user);
    return LoginResponse.builder()
            .token(jwtToken)
            .build();

  }

  @Override
  public IDResponse<Integer> register(RegisterReq registerReq, String roleName) {
    userRepository.findByUsername(registerReq.getUsername()).ifPresent(user -> {
      throw new AppException(AuthErrorCode.USERNAME_EXISTS);
    });
    userRepository.findByEmail(registerReq.getEmail()).ifPresent(user -> {
      throw new AppException(AuthErrorCode.EMAIL_EXISTS);
    });
    Role role = roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new AppException(ErrorCodeUtils.ERROR));
    User user = User.builder()
            .username(registerReq.getUsername())
            .password(passwordEncoder.encode(registerReq.getPassword()))
            .email(registerReq.getEmail())
            .fullName(registerReq.getFullName())
            .phone(registerReq.getPhone())
            .address(registerReq.getAddress())
            .roles(Collections.singleton(role))
            .build();

    userRepository.save(user);
    return IDResponse.<Integer>builder()
            .id(userRepository.save(user).getUserId())
            .build();
  }

  @Override
  public VerifyResponse verifyToken(VerifyReq verifyRequest) {
    String token = verifyRequest.getToken();
    if (token == null || token.isEmpty()) {
      throw new AppException(ErrorCodeUtils.UNAUTHENTICATED);
    }
    org.springframework.security.core.userdetails.User user = JwtTokenUtils.validate(token, SECRET_KEY);
    String username = user.getUsername();
    userRepository.findByUsername(username).orElseThrow(() -> new AppException(AuthErrorCode.USERNAME_NOT_EXISTS));

    return VerifyResponse.builder()
            .isValid(true)
            .build();
  }
}
