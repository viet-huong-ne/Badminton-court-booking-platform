package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.BookingDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private int courtID;
    private int customerId;
    private Date bookingDate;
    private String bookingType;
    private List<BookingDetails> bookingDetails;

}
