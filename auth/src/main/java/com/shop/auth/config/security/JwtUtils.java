package com.shop.auth.config.security;

import com.shop.auth.entity.Role;
import com.shop.auth.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vuongdev.common.exception.AppException;
import org.vuongdev.common.exception.ErrorCodeUtils;
import org.vuongdev.common.utils.JwtTokenUtils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {
  @Value("${jwt.expiration}")
  private int EXPIRATION;

  @Value("${jwt.secretKey}")
  private String secretKey;

  // functions to generate and verify JWT tokens
  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    Collection<String> roles = user.getRoles().stream()
            .map(Role::getRoleName)
            .toList();
    claims.put("roles", roles);
    claims.put("email", user.getEmail());
    claims.put("fullName", user.getFullName());
    try {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(user.getUsername())
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000L))
              .signWith(JwtTokenUtils.getSignInKey(secretKey), SignatureAlgorithm.HS256)
              .compact();
    }
    catch (Exception e) {
      throw new AppException(ErrorCodeUtils.ERROR);
    }
  }

  // functions to generate and verify JWT tokens for email verification
//  public String generateTokenVerifyEmail(User user) {
//    Map<String, Object> claims = new HashMap<>();
//    claims.put("userId", user.getUserId());
//    try {
//      return Jwts.builder()
//              .setClaims(claims)
//              .setSubject(user.getUsername())
//              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_VERIFY_EMAIL * 1000L))
//              .signWith(getSignInKeyVerifyEmail(), SignatureAlgorithm.HS256)
//              .compact();
//    }
//    catch (Exception e) {
//      throw new AppException(ErrorCode.ERROR);
//    }
//  }

//  private Key getSignInKeyVerifyEmail() {
//    byte[] bytes = Decoders.BASE64.decode(SECRET_KEY_VERIFY_EMAIL);
//    return Keys.hmacShaKeyFor(bytes);
//  }
}
