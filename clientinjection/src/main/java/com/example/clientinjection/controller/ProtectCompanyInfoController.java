package com.example.clientinjection.controller;


import com.example.clientinjection.controller.dto.ProtectCompanyInfoControllerDto;
import com.example.clientinjection.entity.CompanyInfo;
import com.example.clientinjection.entity.UserInfo;
import com.example.clientinjection.repository.UserInfoRepository;
import com.example.clientinjection.service.CompanyInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProtectCompanyInfoController {

    private final CompanyInfoService companyInfoService;
    private final UserInfoRepository userInfoRepository;

    @GetMapping("/company-info")
    public CompanyInfo getCompanyInfo(
            @CookieValue(name = "userId") String userId
    ) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow();
        return companyInfoService.getCompanyInfo(userInfo.getCompanyId()).orElse(null);
    }

    @PutMapping("/company-info/{companyId}")
    public CompanyInfo updateCompanyInfo(
            @Valid
            @RequestBody ProtectCompanyInfoControllerDto.UpdateRequest updateRequest,
            @PathVariable String companyId,
            @CookieValue(name = "userId") String userId
            ) {

        if (companyInfoService.getCompanyInfo(companyId).isEmpty()) {
            throw new IllegalArgumentException("Not found companyId : " + companyId);
        }

        CompanyInfo companyInfo = companyInfoService.getCompanyInfo(updateRequest.getCompanyId()).orElseThrow();
        UserInfo userInfo= userInfoRepository.findById(userId).orElseThrow();

        log.info("userId : {} companyId : {}", userInfo.getUserId(), userInfo.getCompanyId());

        if (!Objects.equals(companyInfo.getCompanyId(), userInfo.getCompanyId())) {
            throw new IllegalArgumentException("Not has permission");
        }



        companyInfo
                .setCompanyName(updateRequest.getCompanyName())
                .setCompanyAddress(updateRequest.getCompanyAddress())
                .setCompanyTaxId(updateRequest.getCompanyTaxId());


        return companyInfoService.updateCompanyInfo(companyInfo);
    }

}
