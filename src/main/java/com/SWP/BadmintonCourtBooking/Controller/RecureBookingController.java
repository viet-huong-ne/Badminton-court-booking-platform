package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.BookingDay;
import com.SWP.BadmintonCourtBooking.Dto.BookingResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.RecureBooDTO;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringBookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.SubCourtAvailabilityRequest;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/recure-booking")
public class RecureBookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingService bookingService;
    //TODO API THÊM ĐẶT LỊCH CỐ ĐỊNH
    @PostMapping("/add-booking")
    public ResponseEntity<String> addBooking(@RequestBody RecurringBookingRequest requestDto) {
//        if (requestDto.getStartTime() == null || requestDto.getEndTime() == null
//                || requestDto.getEndDate() == null || requestDto.getStartDate() == null
//                || requestDto.getListDayOfWeek() == null || requestDto.getListSubCourt() == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        List<BookingRequest> listDetailboooking = new ArrayList<>();
//        for (BookingDay b : requestDto.getListDayOfWeek()) {
//            BookingRequest detail = new BookingRequest();
//            detail.setBookingDate(b.getDayTime());
//            detail.setStartTime(requestDto.getStartTime());
//            detail.setEndTime(requestDto.getEndTime());
//            detail.setCourtID(requestDto.getCourtId());
//            ResponseCourtDto dto = bookingService.checkCourtAvailability(detail);
//            String submess = "";
//
//            for (Integer id : requestDto.getListSubCourt()) {
//                for (SubCourt c : dto.getSubCourt()) {
//                    System.out.println(id);
//                    System.out.println(c.getSubCourtID() + " ---" + c.isSubCourtStatus());
//                    if (c.getSubCourtID() == id) {
//                        if (c.isSubCourtStatus() == false) {
//                            submess = "Sub-Court " + c.getSubCourtName() + " slot not available!";
//
//                        }
//                    }
//                }
//            }
//            System.out.println(submess);
//            String mess = "{ \"mess\": '" + submess + "'}";
//            System.out.println(mess);
//            if (submess != "") {
//                return ResponseEntity.badRequest().body(mess);
//            }
//        }
        double totalPrice = bookingService.saveRecureBooking(requestDto);
        String resp = "{ \"totalPrice\": '" + totalPrice + "'}";
        return ResponseEntity.ok().body(resp);
    }

//    @GetMapping("GetAvailableSubCourt")
//    public ResponseEntity<ResponseCourtDto> GetAvailableSubCourt(@RequestParam("court_id") int courtId, @RequestParam("startDate") LocalDate startDate,
//                                                                 @RequestParam("endDate") LocalDate endDate, @RequestParam("dayOfWeek") String dayOfWeek,
//                                                                 @RequestParam("startTime") LocalTime startTime, @RequestParam("endTime") LocalTime endTime) {
//
//        ResponseCourtDto responseCourtDto = bookingService.getListAvailableSubCourt(courtId, startDate, endDate, dayOfWeek.toUpperCase(), startTime, endTime);
//        return ResponseEntity.ok(responseCourtDto);
//    }
    //TODO API TRẢ VỀ TOTAL PRICE
    @PostMapping("/totalPrice")
    public ResponseEntity<String> getTotalPrice(@RequestBody RecurringRequest requestDto) {
        double totalPrice = bookingService.getTotalPriceOfRecureBooking(requestDto);
        String resp = "{ \"totalPrice\": '" + totalPrice + "'}";
        return ResponseEntity.ok().body(resp);
    }
    //TODO API CHECK TRẠNG THÁI SÂN
    @PostMapping("/GetAvailableSubCourt")
    public List<SubCourt> getAvailableSubCourt(@RequestBody SubCourtAvailabilityRequest request) {
        List<DayOfWeek> daysOfWeek = request.getDayOfWeek().stream()
                .map(day -> DayOfWeek.valueOf(day.toUpperCase()))
                .collect(Collectors.toList());
        List<SubCourt> availableSubCourts = bookingService.checkSubCourtAvailability(
                request.getCourtId(),
                request.getStartDate(),
                request.getEndDate(),
                daysOfWeek,
                request.getStartTime(),
                request.getEndTime()
        );
        return availableSubCourts;
    }
}
