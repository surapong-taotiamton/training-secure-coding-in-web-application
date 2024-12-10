package com.example.clientinjection.service;

import com.example.clientinjection.entity.UserInfo;
import com.example.clientinjection.repository.UserInfoRepository;
import com.example.clientinjection.service.spec.AuthenticationServiceSpec;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicAuthenticationService implements AuthenticationService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public AuthenticationServiceSpec.VerifyCredentialResponse verifyCredential(AuthenticationServiceSpec.VerifyCredentialRequest request) {

        log.debug("VerifyCredentialRequest : {}", request);

        Optional<UserInfo> userInfoInDb  = userInfoRepository.findOneByUsername(request.getUsername());

        if (userInfoInDb.isEmpty()) {
            log.info("Case not found username");
            return new AuthenticationServiceSpec.VerifyCredentialResponse()
                    .setSuccess(false);
        } else {
            if ( userInfoInDb.get().getPassword().equals(request.getPassword()) ) {

                return new AuthenticationServiceSpec.VerifyCredentialResponse()
                        .setSuccess(true)
                        .setUserId(userInfoInDb.get().getUserId());
            } else {
                return new AuthenticationServiceSpec.VerifyCredentialResponse()
                        .setSuccess(false);
            }
        }
    }

    @Override
    public String createAndSaveAccessToken(String userId) {
        UserInfo userInfo  = userInfoRepository.findById(userId).orElseThrow();
        String accessToken = RandomStringUtils.secure().nextAlphanumeric(50);
        userInfo.setAccessToken(accessToken);
        userInfoRepository.save(userInfo);
        return accessToken;
    }

    @Override
    public boolean verifyAccessToken(AuthenticationServiceSpec.VerifyAccessTokenRequest request) {

        UserInfo userInfo  = userInfoRepository.findById(request.getUserId()).orElse(null);

        if (userInfo == null) {
            return false;
        }
        return Objects.equals(request.getAccessToken(), userInfo.getAccessToken());
    }
}
