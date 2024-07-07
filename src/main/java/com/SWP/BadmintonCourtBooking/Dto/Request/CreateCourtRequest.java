package com.SWP.BadmintonCourtBooking.Dto.Request;

import com.SWP.BadmintonCourtBooking.Entity.Price;
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
public class CreateCourtRequest {
    private String courtName;

    private String district;

    private String courtAddress;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer courtQuantity;

    private Integer duration;

    private List<String> images;

    private List<Price> price;

    private Integer userID;

    private Integer statusCourt;

    private List<String> serviceCourt;

    //private Set<ServiceCourt> serviceCourt;

    //private List<Price> listPrice;
}
