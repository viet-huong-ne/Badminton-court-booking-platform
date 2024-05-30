package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SlotOfCourt;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private List<SubCourt> subCourt;

    private List<Price> price;

    private List<SlotOfCourt> slotOfCourt;
}
