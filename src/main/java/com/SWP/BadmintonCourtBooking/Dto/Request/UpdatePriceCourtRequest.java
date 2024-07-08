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
public class UpdatePriceCourtRequest {
    private Integer courtID;

//    private String courtName;
//
//    private String district;
//
//    private String courtAddress;
//
//    private LocalTime startTime;
//
//    private LocalTime endTime;
//
//    private Integer courtQuantity;
//
//    private Integer duration;
//
//    private Integer userID;

    //private List<Images> images;

    private List<Price> price;

    //private List<String> serviceCourt;

    /*

    "courtName": "FER AHAHA",
  "district": "THỦ ĐỨC",
  "courtAddress": "FERABCD",
  "startTime": "05:00:00",
  "endTime": "23:00:00",
  "courtQuantity": 3,
  "duration": 60,
  "userID": 3,
  "serviceCourt": ["WIFI"],
     */

}
