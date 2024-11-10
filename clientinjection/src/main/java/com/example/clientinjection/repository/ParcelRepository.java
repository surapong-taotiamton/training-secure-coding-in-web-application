package com.example.clientinjection.repository;

import com.example.clientinjection.entity.Parcel;
import com.example.clientinjection.service.spec.ParcelSpec;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ParcelRepository extends JpaRepository<Parcel, String>, JpaSpecificationExecutor<Parcel> {

    List<Parcel> findByCompanyIdAndParcelTrackingNo(String companyId, String parcelTrackingNo);

    List<Parcel> findByCompanyId(String companyId);

    default List<Parcel> findBySpec(ParcelSpec.SearchParcel searchParcel) {
        return this.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Equal conditions
            if (searchParcel.getParcelTrackingNo() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Parcel.Fields.parcelTrackingNo),
                        searchParcel.getParcelTrackingNo()));
            }

            if (searchParcel.getCompanyId() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Parcel.Fields.companyId),
                        searchParcel.getCompanyId()));
            }

            if (searchParcel.getSenderMobileNo() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Parcel.Fields.senderMobileNo),
                        searchParcel.getSenderMobileNo()));
            }

            if (searchParcel.getReceiverMobileNo() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Parcel.Fields.receiverMobileNo),
                        searchParcel.getReceiverMobileNo()));
            }

            // Like conditions (contains search)
            if (searchParcel.getSenderName() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Parcel.Fields.senderName)),
                        "%" + searchParcel.getSenderName().toLowerCase() + "%"
                ));
            }

            if (searchParcel.getSenderAddress() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Parcel.Fields.senderAddress)),
                        "%" + searchParcel.getSenderAddress().toLowerCase() + "%"
                ));
            }

            if (searchParcel.getReceiverName() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Parcel.Fields.receiverName)),
                        "%" + searchParcel.getReceiverName().toLowerCase() + "%"
                ));
            }

            if (searchParcel.getReceiverAddress() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Parcel.Fields.receiverAddress)),
                        "%" + searchParcel.getReceiverAddress().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        }, Sort.by(Parcel.Fields.parcelTrackingNo));
    }


}
