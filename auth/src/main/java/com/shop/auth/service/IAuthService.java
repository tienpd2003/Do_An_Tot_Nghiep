package com.shop.auth.service;

import com.shop.auth.dto.request.LoginReq;
import com.shop.auth.dto.request.RegisterReq;
import com.shop.auth.dto.request.VerifyReq;
import com.shop.auth.dto.response.LoginResponse;
import com.shop.auth.dto.response.VerifyResponse;
import org.vuongdev.common.dto.response.IDResponse;

public interface IAuthService {
  LoginResponse login(LoginReq loginReq);

  IDResponse<Integer> register(RegisterReq registerReq, String roleName);
  VerifyResponse verifyToken(VerifyReq verifyRequest);
}
