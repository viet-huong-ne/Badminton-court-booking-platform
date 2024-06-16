package com.SWP.BadmintonCourtBooking.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SlotOfCourtDto {

    private LocalTime openTime;

    private LocalTime closeTime;

    private String activeStatus;
}
