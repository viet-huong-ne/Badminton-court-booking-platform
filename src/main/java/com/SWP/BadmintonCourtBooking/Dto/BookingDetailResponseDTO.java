package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailResponseDTO {
    private Double price;
    private LocalTime startTime;
    private LocalTime endTime;
    private int quantity;
    private String subCourtName;
}
