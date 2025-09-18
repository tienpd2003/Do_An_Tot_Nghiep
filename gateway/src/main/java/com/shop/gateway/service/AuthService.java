package com.shop.gateway.service;

import com.shop.gateway.dto.req.VerifyReq;
import com.shop.gateway.dto.res.Response;
import com.shop.gateway.dto.res.VerifyRes;
import com.shop.gateway.repository.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthClient authClient;

  public Mono<Response<VerifyRes>> verifyToken(String token) {
    return authClient.verifyToken(VerifyReq.builder()
            .token(token)
            .build());
  }

}
