package com.example.clientinjection.service;

import com.example.clientinjection.repository.ParcelRepository;
import com.example.clientinjection.service.spec.ParcelSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("jpaSpecificationParcelService")
public class JpaSpecificationParcelService implements ParcelService {

    private final ParcelRepository parcelRepository;
    @Override
    public List<ParcelSpec.ParcelInformation> search(ParcelSpec.SearchParcel search) {
        return parcelRepository.findBySpec(search).stream().map(parcel -> {
            return new ParcelSpec.ParcelInformation()
                    .setParcelId( parcel.getParcelId() )
                    .setCompanyId( parcel.getCompanyId() )
                    .setParcelTrackingNo(parcel.getParcelTrackingNo())
                    .setSenderName(parcel.getSenderName())
                    .setSenderMobileNo(parcel.getSenderMobileNo())
                    .setSenderAddress(parcel.getSenderAddress())
                    .setReceiverName(parcel.getReceiverName())
                    .setReceiverMobileNo(parcel.getReceiverMobileNo())
                    .setReceiverAddress(parcel.getReceiverAddress())
                    .setWeightInGram(parcel.getWeightInGram())
                    .setPrice(parcel.getPrice())
                    .setParcelDescription(parcel.getParcelDescription())
                    ;
        }).toList();
    }
}
