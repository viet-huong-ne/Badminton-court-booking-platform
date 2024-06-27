package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringBookingRequest;
import com.SWP.BadmintonCourtBooking.Entity.*;
import com.SWP.BadmintonCourtBooking.Repository.*;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
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
    private Booking tmpBooking;
    private BookingResponseDTO responseBookingDTO;

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RecurringBookingRepository recurringBookingRepository;

    @Override
    public ResponseCourtDto checkCourtAvailability(BookingRequest bookingRequest) {
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(bookingRequest.getCourtID());
        LocalTime startTime = bookingRequest.getStartTime();
        LocalTime endTime = bookingRequest.getEndTime();
        ResponseCourtDto responseCourtDto;
        List<BookingDetails> bookingDetails = new ArrayList<>();
        List<Booking> booking = bookingRepository.findByBookingDate(bookingRequest.getBookingDate(), bookingRequest.getCourtID());
        bookingDetails = bookingDetailsRepository.findExistingTime(bookingRequest.getStartTime(), bookingRequest.getEndTime(), bookingRequest.getCourtID(), bookingRequest.getBookingDate());

        for (SubCourt x : subCourts) {
            for (BookingDetails y : bookingDetails) {
                if (x.getSubCourtID() == y.getSubCourt().getSubCourtID()) x.setSubCourtStatus(false);

            }
        }

        responseCourtDto = new ResponseCourtDto(bookingRequest.getCourtID(), subCourts, bookingRequest.getBookingDate(), bookingRequest.getStartTime(), bookingRequest.getEndTime());
        subCourts = new ArrayList<>();
        lastAvailabilityCheck = responseCourtDto;
        return responseCourtDto;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public ResponseCourtDto checkRecurring(BookingRequest bookingRequest) {
        DayOfWeek day = bookingRequest.getBookingDate().getDayOfWeek();
        boolean status = false;
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(bookingRequest.getCourtID());
        List<RecurringBooking> booking = recurringBookingRepository.findRecuByCourtID(bookingRequest.getCourtID(), bookingRequest.getStartTime(), bookingRequest.getEndTime() );
        for (RecurringBooking x : booking) {
            for (DayOfWeek y : x.getDaysOfWeek()){

            }
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------
    @Override
    public ResponseCourtDto getLastAvailabilityCheck() {
        return lastAvailabilityCheck;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public double preBooking(BookingDto bookingDto) {

        double totalPrice = calTotalPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime(), bookingDto.getBookingDate()) * bookingDto.getBookingDetails().size();

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        return totalPrice;
    }

    @Override
    public BookingResponseDTO saveBooking(BookingDto bookingDto) {
        //Double tmp = priceRepository.getPriceOfSlot(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getStartTime().plusHours(1));
        Double tmp = getPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getStartTime().plusMinutes(30), bookingDto.getBookingDate());
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
        //double totalPrice = calTotalPrice(bookingDto.getCourtID(),bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime())* bookingDetails.size();
        double totalPrice = calTotalPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime(), bookingDto.getBookingDate()) * bookingDetails.size();
        booking.setTotalPrice(totalPrice);
        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        Booking savedBooking = bookingRepository.save(booking);
        return convertToResponseDTO(savedBooking);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    public Booking saveBookingIfUserPaid(String payCode) {
        // User user = userRepository.findById(bookingDto.getCustomerId()).orElseThrow(() -> new RuntimeException("User not found"));
        //LocalDateTime now = LocalDateTime.now();
        Payment payment = paymentRepository.findByUserIdAndPayCode(tmpBooking.getUser().getUserID(), payCode);

        if (payment != null && payment.getPaymentStatus().equals("Successfully")) {
            //LocalDateTime now = LocalDateTime.now();
            //Duration duration = Duration.between(payment.getPaymentTime(), now);
            //long minutesDifference = duration.toMinutes();

            // Allow a difference of up to 5 minutes

            return bookingRepository.save(tmpBooking);
        } else {
            throw new IllegalStateException("User has not completed payment for this booking");
        }

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//PHƯƠNG THỨC CONVERT VÀ TRẢ VỀ NHỮNG THÔNG TIN CẦN THIẾT CHO CLIENTS
    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO responseDTO = new BookingResponseDTO();

        responseDTO.setCustomerName(booking.getUser().getLastName());
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

    private double calTotalPrice(int courtID, LocalTime startTime, LocalTime endTime, LocalDate date) {
        List<LocalTime> times = new ArrayList<>();
        double sum = 0;
        while (!startTime.isAfter(endTime)) {
            times.add(startTime);
            startTime = startTime.plusMinutes(30);
        }
        for (int i = 0; i < times.size() - 1; i++) {
            //Double tmp = priceRepository.getPriceOfSlot(courtID,times.get(i), times.get(i + 1), dayOfWeek)/2;
            double tmp = getPrice(courtID, times.get(i), times.get(i + 1), date) / 2;
            sum += tmp;
        }
        return sum;
    }

    public double getPrice(int courtId, LocalTime startTime, LocalTime endTime, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String activeStatus;

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            activeStatus = "CT";
        } else {
            activeStatus = "TT";
        }

        List<Price> priceList = priceRepository.getPriceByCourtID(courtId);

        for (Price pricing : priceList) {
            if (("ALL".equals(pricing.getActiveStatus()) || pricing.getActiveStatus().equals(activeStatus))
                    && (startTime.isAfter(pricing.getOpenTime()) || startTime.equals(pricing.getOpenTime()))
                    && (endTime.isBefore(pricing.getCloseTime()) || endTime.equals(pricing.getCloseTime()))) {
                return pricing.getUnitPrice();
            }
        }
        throw new IllegalArgumentException("No price found for the given date and time");
    }
}
