package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailResponseDTO {
    private Double price;
    private LocalTime startTime;
    private LocalTime endTime;
    private int quantity;
    private String subCourtName;
}
