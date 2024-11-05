package com.example.clientinjection.repository;

import com.example.clientinjection.entity.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, String> {



}
