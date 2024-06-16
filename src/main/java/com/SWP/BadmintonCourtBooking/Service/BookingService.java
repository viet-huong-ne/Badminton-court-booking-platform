package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.BookingDto;
import com.SWP.BadmintonCourtBooking.Dto.BookingResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.ResponseBooking;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    //void bookCourt(Booking booking);
    ResponseCourtDto checkCourtAvailability(ResponseBooking responseBooking);
    ResponseCourtDto getLastAvailabilityCheck();
    BookingResponseDTO saveBooking(BookingDto bookingDTO);

}
