package com.example.clientinjection.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@FieldNameConstants
@Accessors(chain = true)
@Data
@Entity
@Table(name = "parcel")
public class Parcel {

    @Id
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
