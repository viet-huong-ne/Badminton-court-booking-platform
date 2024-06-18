package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SlotOfCourt;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourtDto {
    private Integer  courtID;

    private String courtName;

    private String District;

    private String courtAddress;

    private Integer  courtQuantity;

    private Integer  duration;

    private List<SubCourtDto> subCourt;

    private List<Price> price;

   // private List<SlotOfCourtDto> slotOfCourt;
}
