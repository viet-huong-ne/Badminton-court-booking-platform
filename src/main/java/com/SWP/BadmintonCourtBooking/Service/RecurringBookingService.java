package com.SWP.BadmintonCourtBooking.Service;


import com.SWP.BadmintonCourtBooking.Dto.BookingResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.BookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.AllBooking;
import com.SWP.BadmintonCourtBooking.Entity.Booking;

import java.util.List;

public interface RecurringBookingService {
    List<BookingResponse> getBookingForStaff(Integer staffID);
    AllBooking checkInBooking(int bookingID);
}