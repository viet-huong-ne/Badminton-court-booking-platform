package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestBookingDto {
    private int courtID;
    private int customerId;
    private LocalDate bookingDate;
    private String bookingType;
    private List<BookingDetailsDto> bookingDetailsDto;
}
