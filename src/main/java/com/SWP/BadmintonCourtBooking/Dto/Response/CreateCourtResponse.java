package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourtResponse {
    /*
    private Court court;
    */
//    private Integer courtID;

    private String courtName;

    private String district;

    private String courtAddress;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer duration;

    private List<Images> images;

    //private Integer courtQuantity;

    private Integer userID;

    private String phone;

    private Integer statusCourt;

    private List<SubCourt> subCourts;

    private List<ServiceCourt> serviceCourt;

    private List<Price> price;

    //private Set<ServiceCourt> serviceCourt;


}
