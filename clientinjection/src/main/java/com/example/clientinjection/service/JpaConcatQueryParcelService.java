package com.example.clientinjection.service;

import com.example.clientinjection.entity.Parcel;
import com.example.clientinjection.service.spec.ParcelSpec;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service("jpaConcatQueryParcelService")
public class JpaConcatQueryParcelService implements ParcelService {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ParcelSpec.ParcelInformation> search(ParcelSpec.SearchParcel search) {

        String query = " SELECT t1 FROM Parcel t1  WHERE 1 = 1" ;

        query += String.format(" AND t1.companyId =  '%s' ", search.getCompanyId());

        if (search.getParcelTrackingNo() != null && !search.getParcelTrackingNo().isEmpty() ) {
            query += String.format(" AND t1.parcelTrackingNo = '%s' ", search.getParcelTrackingNo());
        }

        log.info("query : {}", query);

        List<Parcel> parcelList = entityManager.createQuery(query, Parcel.class).getResultList();

        return parcelList.stream().map(tuple -> {
            return new ParcelSpec.ParcelInformation()
                    .setParcelId( tuple.getParcelId() )
                    .setCompanyId( tuple.getCompanyId() )
                    .setParcelTrackingNo(tuple.getParcelTrackingNo())
                    .setSenderName(tuple.getSenderName())
                    .setSenderMobileNo(tuple.getSenderMobileNo())
                    .setSenderAddress(tuple.getSenderAddress())
                    .setReceiverName(tuple.getReceiverName())
                    .setReceiverMobileNo(tuple.getReceiverMobileNo())
                    .setReceiverAddress(tuple.getReceiverAddress())
                    .setWeightInGram(tuple.getWeightInGram())
                    .setPrice(tuple.getPrice())
                    .setParcelDescription(tuple.getParcelDescription())
                    ;
        }).toList();
    }

}
