package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Entity.Booking;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    void bookCourt(Booking booking);

}
