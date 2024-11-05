package com.example.clientinjection.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    private String companyId;

    private String companyName;

    private String companyTaxId;

    private String companyAddress;

    private Long countUseParcel;

}
