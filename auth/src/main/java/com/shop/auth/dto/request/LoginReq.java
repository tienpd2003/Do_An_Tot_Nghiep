package com.shop.auth.dto.request;

import com.shop.auth.constant.Message;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    @NotBlank(message = Message.LOGIN_USERNAME_REQUIRE)
    private String username;
    @NotBlank(message = Message.LOGIN_PASSWORD_REQUIRE)
    private String password;
}
