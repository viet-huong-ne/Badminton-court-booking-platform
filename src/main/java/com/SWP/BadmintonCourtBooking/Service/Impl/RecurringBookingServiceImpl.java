package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.BookingDetailResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.BookingResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Dto.Response.BookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.*;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
import com.SWP.BadmintonCourtBooking.Repository.*;
import com.SWP.BadmintonCourtBooking.Service.RecurringBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecurringBookingServiceImpl implements RecurringBookingService {

    @Autowired
    private AllBookingRepository allBookingRepository;
    @Autowired
    private RecurringBookingRepository recurringBookingRepository;
    @Autowired
    private CourtRepository courtRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Override
    public List<BookingResponse> getBookingForStaff(Integer staffID) {
        Staff staff = staffRepository.findCourtIDByUserID(staffID);
        if (staff == null) {
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        }
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        //List<Booking> list = bookingRepository.findBookingsWithinCurrentWeek(startDate, endDate, staff.getCourt().getCourtID());
        List<AllBooking> list = allBookingRepository.findAllBookingByCourtID(staff.getCourt().getCourtID());
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (AllBooking booking : list) {
            BookingResponse bookingResponse = convertToBookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }
    private BookingResponseDTO convertToResponseDTO(AllBooking booking) {
        RecurringBooking recurringBooking = recurringBookingRepository.findById(booking.getRecurringBookingID()).orElseThrow();
        Court court = courtRepository.findById(booking.getCourtID()).orElseThrow();
        BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setCustomerName(booking.getLastName());
        responseDTO.setPhone(booking.getPhone());
        responseDTO.setCourtName(court.getCourtName());
        responseDTO.setAddress(court.getCourtAddress());
        responseDTO.setBookingDate(booking.getBookingDate());
        List<BookingDetailResponseDTO> detailResponseDTOs = recurringBooking.getSubCourts().stream()
                .map(detail -> {
                    BookingDetailResponseDTO detailResponseDTO = new BookingDetailResponseDTO();
                    detailResponseDTO.setSubCourtName(detail.getSubCourtName());
                    return detailResponseDTO;
                }).collect(Collectors.toList());

        responseDTO.setBookingDetails(detailResponseDTOs);
        return responseDTO;
    }

    @Override
    public AllBooking checkInBooking(int bookingID) {
        AllBooking allBooking = allBookingRepository.findById(bookingID).orElseThrow();
        allBooking.setStatus(true);
        return allBookingRepository.save(allBooking);
    }

    public BookingResponse convertToBookingResponse(AllBooking booking) {
        RecurringBooking recurringBooking = recurringBookingRepository.findById(booking.getRecurringBookingID()).orElseThrow();
        Court court = courtRepository.findById(booking.getCourtID()).orElseThrow();
        List<BookingDetailResponseDTO> detailResponseDTOs = recurringBooking.getSubCourts().stream()
                .map(detail -> {
                    BookingDetailResponseDTO detailResponseDTO = new BookingDetailResponseDTO();

                    detailResponseDTO.setStartTime(recurringBooking.getStartTime());
                    detailResponseDTO.setEndTime(recurringBooking.getEndTime());
                    detailResponseDTO.setSubCourtName(detail.getSubCourtName());
                    return detailResponseDTO;
                }).collect(Collectors.toList());
        PaymentResDTO paymentResDto = new PaymentResDTO();
//        paymentResDto.setBankCode(booking.getPayment().getBankCode());
//        paymentResDto.setPaymentAmount(booking.getPayment().getPaymentAmount());
//        paymentResDto.setPaymentDate(booking.getPayment().getPaymentTime());
//        paymentResDto.setTransactionCode(booking.getPayment().getTransactionCode());

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .courtName(recurringBooking.getCourt().getCourtName())
                .address(recurringBooking.getCourt().getCourtAddress())
                .courtPhoneNumber(recurringBooking.getCourt().getUser().getPhone())
                .customerName(booking.getLastName())
                .customerPhone(booking.getPhone())
                .totalPrice(recurringBooking.getTotalPrice())
                .bookingDate(booking.getBookingDate())
                .bookingDetails(detailResponseDTOs)
                .status(booking.isStatus())
                .paymentResDTO(paymentResDto).build();
    }
}