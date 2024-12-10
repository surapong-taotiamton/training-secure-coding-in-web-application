package com.example.clientinjection.controller;

import com.example.clientinjection.controller.dto.ProtectSearchParcelControllerDto;
import com.example.clientinjection.entity.UserInfo;
import com.example.clientinjection.repository.UserInfoRepository;
import com.example.clientinjection.service.AuthenticationService;
import com.example.clientinjection.service.ParcelService;
import com.example.clientinjection.service.spec.AuthenticationServiceSpec;
import com.example.clientinjection.service.spec.ParcelSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ProtectSearchParcelController {

    @Autowired
    @Qualifier("jpaSpecificationParcelService")
    private ParcelService parcelService;


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("search-json/parcel")
    public List<ParcelSpec.ParcelInformation> searchParcel(
            @RequestBody ProtectSearchParcelControllerDto.SearchRequest request,
            @CookieValue(name = "userId") String userId,
            @CookieValue(name = "accessToken") String accessToken
            ) {

        AuthenticationServiceSpec.VerifyAccessTokenRequest verifyAccessTokenRequest = new AuthenticationServiceSpec.VerifyAccessTokenRequest()
                .setUserId(userId)
                .setAccessToken(accessToken);

        if (!authenticationService.verifyAccessToken( verifyAccessTokenRequest )) {
            log.info("Case invalid access token");
            throw new IllegalArgumentException("Invalid access token");
        }

        log.info("Pass verify access token");

        if (request.getParcelTrackingNo() == null || request.getParcelTrackingNo().isBlank()) {
            throw new IllegalArgumentException("Not input ParcelTrackingNo");
        }

        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow();

        log.info("userId : {} has companyId : {}", userInfo.getUserId(), userInfo.getCompanyId());

        ParcelSpec.SearchParcel searchParcel = new ParcelSpec.SearchParcel()
                .setParcelTrackingNo(request.getParcelTrackingNo())
                .setCompanyId(userInfo.getCompanyId());

        return parcelService.search(searchParcel);
    }
}
