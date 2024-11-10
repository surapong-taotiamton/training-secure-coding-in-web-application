package com.example.clientinjection.service.spec;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParcelSpec {


    @Accessors(chain = true)
    @Data
    public static class ParcelInformation {

        private String parcelId;
        private String companyId;

        private String parcelTrackingNo;

        private String senderName;
        private String senderMobileNo;
        private String senderAddress;

        private String receiverName;
        private String receiverMobileNo;
        private String receiverAddress;

        private BigDecimal weightInGram;
        private BigDecimal price;

        private String parcelDescription;
    }


    @Accessors(chain = true)
    @Data
    public static class SearchParcel {
        private String parcelTrackingNo;
        private String companyId;

        private String senderName;
        private String senderMobileNo;
        private String senderAddress;

        private String receiverName;
        private String receiverMobileNo;
        private String receiverAddress;

    }

}
