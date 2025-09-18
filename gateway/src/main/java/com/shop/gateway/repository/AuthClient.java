package com.shop.gateway.repository;

import com.shop.gateway.dto.req.VerifyReq;
import com.shop.gateway.dto.res.Response;
import com.shop.gateway.dto.res.VerifyRes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface AuthClient {
  @PostExchange(url = "/auth/auth/verify-token", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<Response<VerifyRes>> verifyToken(@RequestBody VerifyReq verifyRequest);
}
