package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.ResponseBooking;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Service.BookingDetailsService;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
//    @Autowired
//    private BookingDetailsService bookingDetailsService;

    @GetMapping("/check")
    public ResponseCourtDto checkBooking(@RequestBody ResponseBooking responseBooking) {
        if (responseBooking.getStartTime() == null || responseBooking.getEndTime() == null) {
            return null;
        }
        ResponseCourtDto responseCourtDto = bookingService.checkCourtAvailability(responseBooking);
        return responseCourtDto;
    }
}
