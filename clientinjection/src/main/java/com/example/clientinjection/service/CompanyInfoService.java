package com.example.clientinjection.service;

import com.example.clientinjection.entity.CompanyInfo;

import java.util.Optional;

public interface CompanyInfoService {

    Optional<CompanyInfo> getCompanyInfo(String companyId);

    CompanyInfo updateCompanyInfo(CompanyInfo companyInfo);

}
