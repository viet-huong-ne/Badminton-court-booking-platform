package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.BookingDto;
import com.SWP.BadmintonCourtBooking.Dto.BookingResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.RecureBooDTO;
import com.SWP.BadmintonCourtBooking.Dto.Respone.ResponseBooking;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
public interface BookingService {
    //void bookCourt(Booking booking);
    ResponseCourtDto checkCourtAvailability(ResponseBooking responseBooking);
    ResponseCourtDto getLastAvailabilityCheck();
    BookingResponseDTO saveBooking(BookingDto bookingDTO);
    BookingResponseDTO showBill();

    double saveRecureBooking(RecureBooDTO dto);
    ResponseCourtDto getListAvailableSubCourt(int courId, LocalDate startDate, LocalDate endDate, String dayOfWeek, LocalTime startTime , LocalTime endTime);

}
