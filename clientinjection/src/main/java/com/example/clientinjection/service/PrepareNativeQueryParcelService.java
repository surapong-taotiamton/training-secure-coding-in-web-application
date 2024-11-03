package com.example.clientinjection.service;

import com.example.clientinjection.service.spec.ParcelSpec;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("prepareNativeQueryParcelService")
public class PrepareNativeQueryParcelService implements ParcelService {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ParcelSpec.ParcelInformation> search(ParcelSpec.SearchParcel search) {

        HashMap<String, Object> parameter  = new HashMap<>();

        String query = " SELECT * FROM parcel as t1  WHERE 1 = 1" ;
        query += " AND t1.companyId = :companyId ";

        parameter.put("companyId", search.getCompanyId());

        if (search.getParcelTrackingNo() != null && !search.getParcelTrackingNo().isEmpty() ) {
            query += " AND t1.parcelTrackingNo = :parcelTrackingNo ";
            parameter.put("parcelTrackingNo", search.getParcelTrackingNo());
        }
        log.info("query : {}", query);

        final Query prepareQuery = entityManager.createNativeQuery(query, Tuple.class);

        if (parameter.get("companyId") != null) {
            prepareQuery.setParameter("companyId", parameter.get("companyId") );
        }

        if (parameter.get("parcelTrackingNo") != null) {
            prepareQuery.setParameter("parcelTrackingNo", parameter.get("parcelTrackingNo") );
        }


        List<Tuple> tupleList = prepareQuery.getResultList();

        return tupleList.stream().map(tuple -> {
            return new ParcelSpec.ParcelInformation()
                    .setParcelId( tuple.get("parcelId", String.class))
                    .setCompanyId(tuple.get("companyId", String.class))
                    .setParcelTrackingNo(tuple.get("parcelTrackingNo", String.class))
                    .setSenderName(tuple.get("senderName", String.class))
                    .setSenderMobileNo(tuple.get("senderMobileNo", String.class))
                    .setSenderAddress(tuple.get("senderAddress", String.class))
                    .setReceiverName(tuple.get("receiverName", String.class))
                    .setReceiverMobileNo(tuple.get("receiverMobileNo", String.class))
                    .setReceiverAddress(tuple.get("receiverAddress", String.class))
                    .setWeightInGram(tuple.get("weightInGram", BigDecimal.class))
                    .setPrice(tuple.get("price", BigDecimal.class))
                    .setParcelDescription(tuple.get("parcelDescription", String.class))
                    ;
        }).toList();
    }

}