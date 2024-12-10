package com.example.clientinjection.service.spec;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationServiceSpec {

    @Accessors(chain = true)
    @Data
    public static class VerifyCredentialRequest {
        private String username;
        private String password;
    }

    @Accessors(chain = true)
    @Data
    public static class VerifyCredentialResponse {
        private boolean success;
        private String userId;
    }

    @Accessors(chain = true)
    @Data
    public static class VerifyAccessTokenRequest {
        private String userId;
        private String accessToken;
    }

}
