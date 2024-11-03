package com.example.clientinjection.controller;

import com.example.clientinjection.controller.dto.ParcelControllerDto;
import com.example.clientinjection.service.ParcelService;
import com.example.clientinjection.service.spec.ParcelSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ParcelController {

    @Autowired
    @Qualifier("jpaQueryMethodNameParcelService")
    private  ParcelService parcelService;

    @RequestMapping("/search-parcel")
    public String searchParcel(
            @ModelAttribute ParcelControllerDto.SearchParcel request,
            @CookieValue("companyId") String companyId,
            Model model
    ) {

        List<ParcelSpec.ParcelInformation>  resultSearch = parcelService.search(
                new ParcelSpec.SearchParcel()
                        .setParcelTrackingNo(request.getParcelTrackingNo())
                        .setCompanyId(companyId)
        );
        model.addAttribute("parcels", resultSearch);
        return "search-parcel-view.html";
    }

}
