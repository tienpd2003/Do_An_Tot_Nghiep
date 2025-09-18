package com.shop.auth.controller;

import com.shop.auth.constant.Message;
import com.shop.auth.constant.PredefinedRole;
import com.shop.auth.dto.request.LoginReq;
import com.shop.auth.dto.request.RegisterReq;
import com.shop.auth.dto.request.VerifyReq;
import com.shop.auth.dto.response.LoginResponse;
import com.shop.auth.dto.response.VerifyResponse;
import com.shop.auth.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vuongdev.common.dto.response.IDResponse;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.LocalizationUtils;
import org.vuongdev.common.utils.ResponseUtils;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final IAuthService authService;
  private final LocalizationUtils localizationUtils;

  @PostMapping("/login")
  public ResponseEntity<Response<LoginResponse>> login (@Valid @RequestBody LoginReq loginReq) {
    LoginResponse loginResponse = authService.login(loginReq);
    return ResponseUtils.success(loginResponse, localizationUtils.getLocalizedMessage(Message.LOGIN_SUCCESS));
  }

  @PostMapping("/register")
  public ResponseEntity<Response<IDResponse<Integer>>> register(@Valid @RequestBody RegisterReq registerReq) {
    IDResponse<Integer> idResponse = authService.register(registerReq, PredefinedRole.USER);
    return ResponseUtils.success(idResponse, localizationUtils.getLocalizedMessage(Message.REGISTER_SUCCESS));
  }

  @PostMapping("/verify-token")
  public ResponseEntity<Response<VerifyResponse>> verifyToken(@Valid @RequestBody VerifyReq verifyRequest) {
    return ResponseUtils.success(authService.verifyToken(verifyRequest), localizationUtils.getLocalizedMessage(Message.VERIFY_TOKEN_SUCCESS));
  }

//  @GetMapping("/user")
//  public ResponseEntity<Response<String>> findUserById() {
//    return ResponseUtils.success("User endpoint", "User endpoint");
//  }

}
