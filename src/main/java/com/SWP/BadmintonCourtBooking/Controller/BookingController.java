package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ResponseCourtDto responseCourtDto = bookingService.checkCourtAvailability(bookingRequest);
        return responseCourtDto;
    }

    //API GET LẤY CÁC SÂN NHỎ CÓ THỂ ĐẶT
    @GetMapping("/getCourtStatus")
    public ResponseCourtDto getCourtStatus() {
        return bookingService.getLastAvailabilityCheck();
    }

    //API ĐẶT SÂN THEO NGÀY
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingDto bookingDto) {
        //log.info("BookingDto: {}", bookingDto);
        BookingResponseDTO bookingResponse = bookingService.saveBooking(bookingDto);

        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    @GetMapping("/book/ProvisionalInvoice")
    public ResponseEntity<BookingResponseDTO> responseProvisionalInvoice() {
        return new ResponseEntity<>(bookingService.showBill(), HttpStatus.OK);
    }

    //API TRẢ VỀ THÔNG TIN BILL ĐÃ ĐẶT
    @GetMapping("/book/bill")
    public ResponseEntity<BookingResponseDTO> bookBill() {
        return new ResponseEntity<>(bookingService.showBill(), HttpStatus.OK);
    }

}
