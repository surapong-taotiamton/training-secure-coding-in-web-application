package com.example.clientinjection.controller;


import com.example.clientinjection.controller.dto.ProtectCompanyInfoControllerDto;
import com.example.clientinjection.entity.CompanyInfo;
import com.example.clientinjection.service.CompanyInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProtectCompanyInfoController {

    private final CompanyInfoService companyInfoService;

    @GetMapping("/company-info/{companyId}")
    public CompanyInfo getCompanyInfo(@PathVariable String companyId) {
        return companyInfoService.getCompanyInfo(companyId).orElse(null);
    }

    @PutMapping("/company-info/{companyId}")
    public CompanyInfo updateCompanyInfo(
            @Valid
            @RequestBody ProtectCompanyInfoControllerDto.UpdateRequest updateRequest, @PathVariable String companyId) {

        if (companyInfoService.getCompanyInfo(companyId).isEmpty()) {
            throw new IllegalArgumentException("Not found companyId : " + companyId);
        }

        CompanyInfo companyInfo = companyInfoService.getCompanyInfo(updateRequest.getCompanyId()).orElseThrow();

        companyInfo
                .setCompanyName(updateRequest.getCompanyName())
                .setCompanyAddress(updateRequest.getCompanyAddress())
                .setCompanyTaxId(updateRequest.getCompanyTaxId());


        return companyInfoService.updateCompanyInfo(companyInfo);
    }

}
