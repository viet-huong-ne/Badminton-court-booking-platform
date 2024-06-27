package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.BookingDto;
import com.SWP.BadmintonCourtBooking.Dto.BookingResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Booking;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    //void bookCourt(Booking booking);
    ResponseCourtDto checkCourtAvailability(BookingRequest bookingRequest);
    ResponseCourtDto getLastAvailabilityCheck();
    double preBooking(BookingDto bookingDTO);
    BookingResponseDTO saveBooking(BookingDto bookingDTO);
    Booking saveBookingIfUserPaid(String payCode);
    BookingResponseDTO showBill();

}
