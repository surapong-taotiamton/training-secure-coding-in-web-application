package com.example.clientinjection.controller.dto;


import lombok.Data;
import lombok.experimental.Accessors;

public class ProtectSearchParcelControllerDto {


    @Accessors(chain = true)
    @Data
    public static class SearchRequest {
        private String parcelTrackingNo;
    }
}
