package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Entity.*;
import com.SWP.BadmintonCourtBooking.Repository.*;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;
    @Autowired
    private SubCourtRepository subCourtRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourtRepository courtRepository;
    @Autowired
    private PriceRepository priceRepository;
    private ResponseCourtDto lastAvailabilityCheck;

    private BookingResponseDTO responseBookingDTO;

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Override
    public ResponseCourtDto checkCourtAvailability(ResponseBooking responseBooking) {
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(responseBooking.getCourtID());
        LocalTime startTime = responseBooking.getStartTime();
        LocalTime endTime = responseBooking.getEndTime();
        ResponseCourtDto responseCourtDto;
        List<BookingDetails> bookingDetails = new ArrayList<>();
        List<Booking> booking = bookingRepository.findByBookingDate(responseBooking.getBookingDate(), responseBooking.getCourtID());
        bookingDetails = bookingDetailsRepository.findExistingTime(responseBooking.getStartTime(), responseBooking.getEndTime(), responseBooking.getCourtID(), responseBooking.getBookingDate());

        for (SubCourt x : subCourts) {
            for (BookingDetails y : bookingDetails) {
                if (x.getSubCourtID() == y.getSubCourt().getSubCourtID()) x.setSubCourtStatus(false);

            }
        }

        responseCourtDto = new ResponseCourtDto(responseBooking.getCourtID(), subCourts, responseBooking.getBookingDate(), responseBooking.getStartTime(), responseBooking.getEndTime());
        subCourts = new ArrayList<>();
        lastAvailabilityCheck = responseCourtDto;
        return responseCourtDto;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseCourtDto getLastAvailabilityCheck() {
        return lastAvailabilityCheck;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public BookingResponseDTO saveBooking(BookingDto bookingDto) {


        //List<Price> prices = priceRepository.getPriceByCourtID(bookingDto.getCourtID());
//        for (Price x : prices) {
//            if (x.getActiveStatus().equals("ALL")) check = true;
//            if (check ){
//                for(BookingDetailsDto y :bookingDto.getBookingDetails()) {
//                    if (y.getStartTime().)
//                }
//            }
//        }

//        DayOfWeek date = bookingDto.getBookingDate().getDayOfWeek();
//        String status;
//        if (isWeekend(bookingDto.getBookingDate())) {
//             status = "CT";
//        }else status = "TT";

        Double tmp = priceRepository.getPriceOfSlot(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getStartTime().plusHours(1));
        User user = userRepository.findById(bookingDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Court court = courtRepository.findById(bookingDto.getCourtID())
                .orElseThrow(() -> new RuntimeException("Court not found"));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCourt(court);
        booking.setBooking_type("theo ngay");
        booking.setBooking_date(bookingDto.getBookingDate());
        List<BookingDetails> bookingDetails = bookingDto.getBookingDetails().stream()
                .map(detailDTO -> {
                    BookingDetails detail = new BookingDetails();
                    detail.setSubCourt(subCourtRepository.findById(detailDTO.getSubCourtID()).orElseThrow(() -> new RuntimeException("Sub court not found")));
                    detail.setUnitPrice(tmp);
                    detail.setStartTime(detailDTO.getStartTime());
                    detail.setEndTime(detailDTO.getEndTime());
                    Duration duration = Duration.between(detailDTO.getStartTime(), detailDTO.getEndTime());
                    detail.setQuantity((int) duration.toHours());
                    detail.setBooking(booking);
                    return detail;
                }).collect(Collectors.toList());
        booking.setBookingDetails(bookingDetails);
        double totalPrice = calTotalPrice(bookingDto.getCourtID(),bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime())* bookingDetails.size();
        booking.setTotalPrice(totalPrice);
        Booking savedBooking = bookingRepository.save(booking);
        return convertToResponseDTO(savedBooking);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO responseDTO = new BookingResponseDTO();

        responseDTO.setCustomerName(booking.getUser().getFullName());
        responseDTO.setPhone(booking.getUser().getPhone());
        responseDTO.setCourtName(booking.getCourt().getCourtName());
        responseDTO.setAddress(booking.getCourt().getCourtAddress());
        responseDTO.setTotalPrice(booking.getTotalPrice());
        responseDTO.setBookingDate(booking.getBooking_date());
        List<BookingDetailResponseDTO> detailResponseDTOs = booking.getBookingDetails().stream()
                .map(detail -> {
                    BookingDetailResponseDTO detailResponseDTO = new BookingDetailResponseDTO();

                    detailResponseDTO.setPrice(detail.getUnitPrice());
                    detailResponseDTO.setStartTime(detail.getStartTime());
                    detailResponseDTO.setEndTime(detail.getEndTime());
                    detailResponseDTO.setQuantity(detail.getQuantity());
                    detailResponseDTO.setSubCourtName(detail.getSubCourt().getSubCourtName());
                    return detailResponseDTO;
                }).collect(Collectors.toList());

        responseDTO.setBookingDetails(detailResponseDTOs);
        responseBookingDTO = responseDTO;
        return responseDTO;
    }
    @Override
    public BookingResponseDTO showBill() {
        return responseBookingDTO;
    }
    private static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }


    private double calTotalPrice(int courtID, LocalTime startTime, LocalTime endTime) {
        List<LocalTime> times = new ArrayList<>();
        double sum = 0;
        while (!startTime.isAfter(endTime)) {
            times.add(startTime);
            startTime = startTime.plusHours(1);
        }
        for (int i = 0; i < times.size() - 1; i++) {
            Double tmp = priceRepository.getPriceOfSlot(courtID,times.get(i), times.get(i + 1));
            sum += tmp;
        }
        return sum;
    }
}
