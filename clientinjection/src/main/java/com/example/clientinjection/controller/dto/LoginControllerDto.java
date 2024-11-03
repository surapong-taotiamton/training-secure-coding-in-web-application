package com.example.clientinjection.controller.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginControllerDto {

    @Accessors(chain = true)
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
