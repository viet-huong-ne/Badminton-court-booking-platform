package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private String courtName;
    private String address;
    private String customerName;
    private String phone;
    private double totalPrice;
    private LocalDate bookingDate;
    private List<BookingDetailResponseDTO> bookingDetails;
}
