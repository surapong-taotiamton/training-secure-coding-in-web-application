package com.example.clientinjection.controller;

import com.example.clientinjection.controller.dto.LoginControllerDto;
import com.example.clientinjection.entity.UserInfo;
import com.example.clientinjection.repository.UserInfoRepository;
import com.example.clientinjection.service.AuthenticationService;
import com.example.clientinjection.service.spec.AuthenticationServiceSpec;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserInfoRepository userInfoRepository;

    private final AuthenticationService authenticationService;

    @PostMapping("/login-route/{routeTo}")
    public String loginAndRoute(
            @PathVariable String routeTo,
            @ModelAttribute LoginControllerDto.LoginRequest request, HttpServletResponse response
    ) {
        List<String> allowRouteTo = Arrays.asList("company-info", "search-parcel-problem");

        if (!allowRouteTo.contains(routeTo)) {
            log.info("Case routeTo is not in allow list");
            throw new IllegalArgumentException("Not found route");
        }

        return loginAndReturn(request, routeTo, response);
    }



    public String loginAndReturn(LoginControllerDto.LoginRequest request, String routeTo, HttpServletResponse response) {

        log.info("routeTo : {}", routeTo);

        AuthenticationServiceSpec.VerifyCredentialRequest verifyCredentialRequest = new AuthenticationServiceSpec.VerifyCredentialRequest()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword());

        AuthenticationServiceSpec.VerifyCredentialResponse verifyCredentialResponse = authenticationService.verifyCredential(verifyCredentialRequest);

        if (!verifyCredentialResponse.isSuccess()) {
            log.info("Case verify error");
            throw new IllegalArgumentException("Incorrect credential");
        }

        log.info("Case verify credential success");
        String accessToken = authenticationService.createAndSaveAccessToken(verifyCredentialResponse.getUserId());

        log.debug("Create accessToken success : {}", accessToken);

        Cookie userIdCookie = new Cookie("userId", verifyCredentialResponse.getUserId());
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(7 * 24 * 60 * 60);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(userIdCookie);
        response.addCookie(accessTokenCookie);

        log.debug("Set cookie complete");

        return routeTo;

    }

}
