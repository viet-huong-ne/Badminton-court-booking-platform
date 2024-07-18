package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingPaymentRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.AllBookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.BookingResponse;
import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingService bookingService;

    //API check time frame has been set
    @PostMapping("/check")
    public ResponseCourtDto checkBooking(@RequestBody BookingRequest bookingRequest) {
        if (bookingRequest.getStartTime() == null || bookingRequest.getEndTime() == null) {
            return null;
        }
        return bookingService.checkSubCourtStatus(bookingRequest);

    }

    //API GET LẤY CÁC SÂN NHỎ CÓ THỂ ĐẶT
    @GetMapping("/getCourtStatus")
    public ResponseCourtDto getCourtStatus() {
        return bookingService.getLastAvailabilityCheck();
    }

    //API ĐẶT SÂN THEO NGÀY
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> createPreBooking(@RequestBody BookingDto bookingDto) {
        //log.info("BookingDto: {}", bookingDto);
        BookingResponseDTO booking = bookingService.saveBooking(bookingDto);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    //API lưu booking khi đã thanh toán thành công v2
    //TODO: CREATE Booking
    @PostMapping("/book/saveBookingV2")
    public ResponseEntity<Booking> saveBookingV2(@RequestBody BookingPaymentRequest bookingPaymentRequest) {
        if (bookingPaymentRequest.getPaymentDto().getResponseCode().equals("00")) {
            Booking booking = bookingService.saveBookingIfUserPaid(bookingPaymentRequest);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //API trả về số totalPrice
    @PostMapping("/provisionalInvoice")
    public ResponseEntity<Double> responseProvisionalInvoice(@RequestBody BookingDto bookingDto) {

        return new ResponseEntity<>(bookingService.preBooking(bookingDto), HttpStatus.OK);
    }

    //API TRẢ VỀ THÔNG TIN BILL ĐÃ ĐẶT
    @GetMapping("/book/bill")
    public ResponseEntity<BookingResponseDTO> bookBill() {
        return new ResponseEntity<>(bookingService.showBill(), HttpStatus.OK);
    }

    //API XEM LỊCH SỬ ĐẶT LỊCH CỦA USER
    //TODO API XEM LỊCH SỬ ĐẶT SÂN CỦA USER
    @GetMapping("/booked/{userID}")
    public ResponseEntity<?> getBookingOfUser(@PathVariable Integer userID) {
        List<BookingResponse> bookingList = bookingService.getBooking(userID);
        if (bookingList == null) {
            throw new RuntimeException("No booking found for userID: " + userID);
        }
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    //TODO API lay tat ca order của san (OLD VERSION)
    @GetMapping("/AllBookingsOfCourt/{courtID}")
    public ResponseEntity<?> GetBookingOfCourt(@PathVariable Integer courtID) {
        List<BookingResponse> bookingList = bookingService.getBookingOfCourt(courtID);
        if (bookingList == null) {
            throw new RuntimeException("No booking found for this court: " + courtID);
        }
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    //TODO API lay tat ca order (NEW)
    @GetMapping("/AllBookings")
    public ResponseEntity<?> GetAllBooking() {
        AllBookingResponse bookingList = bookingService.getAllBooking();

        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    //TODO API LAY TAT CA CAC ORDER CHO CAC SAN CUNG 1 CHU
    @GetMapping("/AllBookingForCourtOwner/{userID}")
    public ResponseEntity<?> GetAllBookingForCourtOwner(@PathVariable Integer userID) {
        AllBookingResponse bookingList = bookingService.getAllBookingForCourtOnwer(userID);

        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }


    //TODO API LAY TAT CA ORDER TRONG TUAN CHO STAFF CHECK IN
    @GetMapping("/Staff/{userID}")
    public ResponseEntity<?> GetBookingForStaff(@PathVariable Integer userID) {
        List<BookingResponse> bookingList = bookingService.getBookingForStaff(userID);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }
    //TODO API CHECKIN DON DAT
    @PutMapping("/checkin")
    public ResponseEntity<?> checkInBooking(@RequestParam int bookingID) {
        try {
            Booking updatedBooking = bookingService.checkInBooking(bookingID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
