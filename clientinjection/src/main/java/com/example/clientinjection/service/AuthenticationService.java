package com.example.clientinjection.service;

import com.example.clientinjection.service.spec.AuthenticationServiceSpec;

public interface AuthenticationService {
    AuthenticationServiceSpec.VerifyCredentialResponse verifyCredential(AuthenticationServiceSpec.VerifyCredentialRequest request);

    boolean verifyAccessToken(AuthenticationServiceSpec.VerifyAccessTokenRequest request);
    String createAndSaveAccessToken(String userId);

}
