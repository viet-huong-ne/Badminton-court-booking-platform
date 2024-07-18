package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Dto.BookingDay;
import com.SWP.BadmintonCourtBooking.Dto.RecurringBookingDTO;
import lombok.Data;

import java.util.List;
@Data
public class AllBookingResponse {
    private List<BookingDay> bookingDayList;
    private List<RecurringBookingDTO> recurringBookingList;
}
