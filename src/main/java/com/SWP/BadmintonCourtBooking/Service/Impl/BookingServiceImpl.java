package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.ResponseBooking;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Entity.BookingDetails;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Repository.BookingDetailsRepository;
import com.SWP.BadmintonCourtBooking.Repository.BookingRepository;
import com.SWP.BadmintonCourtBooking.Repository.SubCourtRepository;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;
    @Autowired
    private SubCourtRepository subCourtRepository;

    @Override
    public void bookCourt(Booking booking) {

    }

    @Override
    public ResponseCourtDto checkCourtAvailability(ResponseBooking responseBooking) {
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(responseBooking.getCourtID());
//        for (SubCourt x : subCourts) {
//            x.setSubCourtStatus("true");
//        }
        Time startTime = responseBooking.getStartTime();
        Time endTime = responseBooking.getEndTime();
        ResponseCourtDto responseCourtDto;
        List<BookingDetails> bookingDetails = new ArrayList<>();
        List<Booking> booking = bookingRepository.findByBookingDate(responseBooking.getBookingDate(), responseBooking.getCourtID());
        //for (Booking x : booking) {
        bookingDetails = bookingDetailsRepository.findExistingTime(responseBooking.getStartTime(), responseBooking.getEndTime(), responseBooking.getCourtID(), responseBooking.getBookingDate());

        //}

//        for (BookingDetails x  : bookingDetails){
//            if (endTime.after(x.getStartTime()) || startTime.before(x.getEndTime())  || ((startTime.equals(x.getStartTime()) || startTime.after(x.getStartTime())) & (endTime.equals(x.getEndTime()) || endTime.before(x.getEndTime())))){
//
//            }
//
//        }
        for (SubCourt x : subCourts) {
            for (BookingDetails y : bookingDetails) {
                if (x.getSubCourtID() == y.getSubCourt().getSubCourtID()) x.setSubCourtStatus("false");

            }
        }

        responseCourtDto = new ResponseCourtDto(responseBooking.getCourtID(), subCourts, responseBooking.getBookingDate(), responseBooking.getStartTime(), responseBooking.getEndTime());
        subCourts = new ArrayList<>();
        return responseCourtDto;
    }
}
