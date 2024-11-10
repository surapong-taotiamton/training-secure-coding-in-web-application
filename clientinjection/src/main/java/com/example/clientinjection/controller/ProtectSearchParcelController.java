package com.example.clientinjection.controller;

import com.example.clientinjection.controller.dto.ProtectSearchParcelControllerDto;
import com.example.clientinjection.service.ParcelService;
import com.example.clientinjection.service.spec.ParcelSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProtectSearchParcelController {

    @Autowired
    @Qualifier("jpaSpecificationParcelService")
    private ParcelService parcelService;

    @PostMapping("search-json/parcel")
    public List<ParcelSpec.ParcelInformation> searchParcel(
            @RequestBody ProtectSearchParcelControllerDto.SearchRequest request) {

        if (request.getParcelTrackingNo() == null || request.getParcelTrackingNo().isBlank()) {
            throw new IllegalArgumentException("Not input ParcelTrackingNo");
        }

        ParcelSpec.SearchParcel searchParcel = new ParcelSpec.SearchParcel()
                .setParcelTrackingNo(request.getParcelTrackingNo());

        return parcelService.search(searchParcel);
    }
}
