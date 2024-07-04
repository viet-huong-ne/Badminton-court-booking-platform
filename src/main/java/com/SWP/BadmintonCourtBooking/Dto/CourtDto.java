package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourtDto {
    private Integer courtID;

    private String courtName;

    private String District;

    private String courtAddress;

    private Integer  courtQuantity;

    private Integer  duration;

    private List<Images> images;

    private List<SubCourtDto> subCourt;

    private List<Price> price;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer userID;
   // private List<SlotOfCourtDto> slotOfCourt;
}
