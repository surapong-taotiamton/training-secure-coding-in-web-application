package com.example.clientinjection.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

public class ProtectCompanyInfoControllerDto {


    @Accessors(chain = true)
    @Data
    public static class UpdateRequest {

        @NotBlank
        private String companyId;

        @NotBlank
        private String companyName;

        @NotBlank
        private String companyTaxId;

        @NotBlank
        private String companyAddress;

    }

}
