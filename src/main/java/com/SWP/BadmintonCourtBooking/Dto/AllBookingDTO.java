package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Dto.Response.BookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RecurringBookingResponse;
import lombok.Data;

import java.util.List;
@Data
public class AllBookingDTO {
    private List<BookingResponse> bookingResponseList;
    private List<RecurringBookingResponse> recurringBookingResponseList;
}
