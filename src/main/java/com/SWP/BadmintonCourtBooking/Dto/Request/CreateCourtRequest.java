package com.SWP.BadmintonCourtBooking.Dto.Request;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Entity.User;
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

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer courtQuantity;

    private Integer duration;

    private List<String> images;

    private Integer userID;

    private Integer statusCourt;

    //private List<Price> listPrice;
}
