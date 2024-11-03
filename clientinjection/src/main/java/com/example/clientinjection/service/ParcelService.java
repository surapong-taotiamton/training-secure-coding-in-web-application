package com.example.clientinjection.service;

import com.example.clientinjection.service.spec.ParcelSpec;

import java.util.List;

public interface ParcelService {

    List<ParcelSpec.ParcelInformation> search(ParcelSpec.SearchParcel search);

}
