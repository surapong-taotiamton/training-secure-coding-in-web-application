package com.example.clientinjection.service;

import com.example.clientinjection.entity.CompanyInfo;
import com.example.clientinjection.repository.CompanyInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyInfoImpl implements CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;

    @Override
    public Optional<CompanyInfo>  getCompanyInfo(String companyId) {
        return companyInfoRepository.findById(companyId);
    }

    @Override
    public CompanyInfo updateCompanyInfo(CompanyInfo companyInfo) {
        return companyInfoRepository.save(companyInfo);
    }
}
