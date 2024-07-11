package com.SWP.BadmintonCourtBooking.Dto.Request;

import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateInforCourtRequest {

    private Integer courtID;

    private String courtName;

    private String district;

    private String courtAddress;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer courtQuantity;

    private Integer duration;

//    private Integer userID;

//    private String phone;

//    private Integer statusCourt;

    private List<String> images;

    private List<String> serviceCourt;

    private List<Price> price;
}
