package com.example.clientinjection.filter;

import com.example.clientinjection.service.AuthenticationService;
import com.example.clientinjection.service.spec.AuthenticationServiceSpec;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AccessTokenFilter implements Filter {

    private final AuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In AccessTokenFilter doFilter ");

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();

        String userId = null;
        String accessToken = null;

        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                userId = cookie.getValue();
            }

            if ("accessToken".equals(cookie.getName())) {
                accessToken = cookie.getValue();
            }
        }

        AuthenticationServiceSpec.VerifyAccessTokenRequest verifyAccessTokenRequest = new AuthenticationServiceSpec.VerifyAccessTokenRequest()
                .setAccessToken(accessToken)
                .setUserId(userId);

        log.debug("In AccessTokenFilter verifyAccessTokenRequest : {}", verifyAccessTokenRequest);


        if (authenticationService.verifyAccessToken(verifyAccessTokenRequest)) {
            log.info("Case correct access token");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.info("Case incorrect access token");
            httpServletResponse.setStatus(401);
            httpServletResponse.getWriter().write("Incorrect access token");
        }
    }
}
