package com.example.clientinjection.repository;

import com.example.clientinjection.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository extends JpaRepository<Parcel, String> {

    List<Parcel> findByCompanyIdAndParcelTrackingNo(String companyId, String parcelTrackingNo);
    List<Parcel> findByCompanyId(String companyId);

}
