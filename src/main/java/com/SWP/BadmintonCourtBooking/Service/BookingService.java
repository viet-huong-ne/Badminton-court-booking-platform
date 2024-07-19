package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingPaymentRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringBookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.AllBookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.BookingResponse;
import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public interface BookingService {
    //void bookCourt(Booking booking);
    ResponseCourtDto checkCourtAvailability(BookingRequest bookingRequest);
    ResponseCourtDto checkSubCourtStatus(BookingRequest bookingRequest);
    ResponseCourtDto getLastAvailabilityCheck();
    double preBooking(BookingDto bookingDTO);
    BookingResponseDTO saveBooking(BookingDto bookingDTO);
    Booking saveBookingIfUserPaid(BookingPaymentRequest bookingPaymentRequest);
    BookingResponseDTO showBill();
    List<BookingResponse> getBooking(Integer userID);
    List<BookingResponse> getBookingOfCourt(Integer courtID);
    ResponseCourtDto getListAvailableSubCourt(int courId, LocalDate startDate, LocalDate endDate, String dayOfWeek, LocalTime startTime , LocalTime endTime);
    double saveRecureBooking(RecurringBookingRequest dto);
    public double getTotalPriceOfRecureBooking(RecurringRequest dto);
    //ResponseCourtDto getListAvailableSubCourtV2(int courId, LocalDate startDate, LocalDate endDate, List<String> dayOfWeek, LocalTime startTime , LocalTime endTime);
    List<SubCourt> checkSubCourtAvailability(int courtId, LocalDate startDate, LocalDate endDate,
                                             List<DayOfWeek> daysOfWeek, LocalTime startTime, LocalTime endTime);
    Booking checkInBooking(int bookingID);
    List<BookingResponse> getBookingForStaff(Integer staffID);
    AllBookingResponse getAllBooking();
    AllBookingResponse getAllBookingForCourtOnwer(Integer userID);
    AllBookingDTO getAllBookingForUser(Integer userID);
}
