package com.example.clientinjection.controller;

import com.example.clientinjection.controller.dto.LoginControllerDto;
import com.example.clientinjection.entity.UserInfo;
import com.example.clientinjection.repository.UserInfoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserInfoRepository userInfoRepository;

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginControllerDto.LoginRequest request, HttpServletResponse response) {

        Optional<UserInfo> userInfoOptional  = userInfoRepository.findOneByUsername(request.getUsername());

        if (userInfoOptional.isEmpty()) {
            log.info("Case not found username");
            return "error.html";
        } else {
            if ( userInfoOptional.get().getPassword().equals(request.getPassword()) ) {

                Cookie usernameCookie = new Cookie("username", userInfoOptional.get().getUsername());
                Cookie companyIdCookie = new Cookie("companyId", userInfoOptional.get().getCompanyId());

                response.addCookie(usernameCookie);
                response.addCookie(companyIdCookie);
                return "redirect:/search-parcel";
            } else {
                return "error.html";
            }
        }

    }

}
