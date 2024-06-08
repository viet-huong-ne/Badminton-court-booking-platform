package com.SWP.BadmintonCourtBooking.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SlotOfCourtDto {

    private Time openTime;

    private Time closeTime;

    private String activeStatus;
}
