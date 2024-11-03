package com.example.clientinjection.service;

import com.example.clientinjection.entity.Parcel;
import com.example.clientinjection.repository.ParcelRepository;
import com.example.clientinjection.service.spec.ParcelSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("jpaQueryMethodNameParcelService")
public class JpaQueryMethodNameParcelService implements ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    @Override
    public List<ParcelSpec.ParcelInformation> search(ParcelSpec.SearchParcel search) {

        List<Parcel> resultSearch = null;

        if (search.getParcelTrackingNo() != null && !search.getParcelTrackingNo().isEmpty() ) {
            log.info("Case search with CompanyId and ParcelTrackingNo");
            resultSearch = parcelRepository.findByCompanyIdAndParcelTrackingNo(search.getCompanyId(), search.getParcelTrackingNo());
        } else {
            log.info("Case search with CompanyId");
            resultSearch = parcelRepository.findByCompanyId(search.getCompanyId());
        }

        return resultSearch.stream().map(parcel -> {
            return new ParcelSpec.ParcelInformation()
                    .setParcelId(parcel.getParcelId())
                    .setCompanyId(parcel.getCompanyId())
                    .setParcelTrackingNo(parcel.getParcelTrackingNo())
                    .setSenderName(parcel.getSenderName())
                    .setSenderMobileNo(parcel.getSenderMobileNo())
                    .setSenderAddress(parcel.getSenderAddress())
                    .setReceiverName(parcel.getReceiverName())
                    .setReceiverMobileNo(parcel.getReceiverMobileNo())
                    .setReceiverAddress(parcel.getReceiverAddress())
                    .setWeightInGram(parcel.getWeightInGram())
                    .setPrice(parcel.getPrice())
                    .setParcelDescription(parcel.getParcelDescription());
        }).toList();
    }
}
