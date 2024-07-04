package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourtDto {
    private Integer courtID;

    private String courtName;

    private String District;

    private String courtAddress;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer courtQuantity;

    private Integer duration;

    private List<Images> images;

    private List<SubCourt> subCourts;

    private List<Price> price;

    private Integer userID;

    private String phone;
}
