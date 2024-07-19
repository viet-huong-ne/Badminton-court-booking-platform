package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingPaymentRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;

import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringBookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.AllBookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.BookingResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RecurringBookingResponse;
import com.SWP.BadmintonCourtBooking.Entity.*;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
import com.SWP.BadmintonCourtBooking.Repository.*;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import com.SWP.BadmintonCourtBooking.Service.UserService;
import jakarta.transaction.Transactional;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
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
    @Autowired
    private AllBookingRepository allbookingRepository;

    private ResponseCourtDto lastAvailabilityCheck;
    private BookingResponseDTO responseBookingDTO;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RecurringBookingRepository recurringBookingRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AllBookingRepository allBookingRepository;

    @Override
    public ResponseCourtDto checkCourtAvailability(BookingRequest bookingRequest) {
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(bookingRequest.getCourtID());
        List<BookingDetails> bookingDetails = bookingDetailsRepository.findExistingTime(bookingRequest.getStartTime(), bookingRequest.getEndTime(), bookingRequest.getCourtID(), bookingRequest.getBookingDate());

        for (SubCourt x : subCourts) {
            for (BookingDetails y : bookingDetails) {
                if (x.getSubCourtID().equals(y.getSubCourt().getSubCourtID())) x.setSubCourtStatus(false);

            }
        }

        ResponseCourtDto responseCourtDto = new ResponseCourtDto(bookingRequest.getCourtID(), subCourts, bookingRequest.getBookingDate(), bookingRequest.getStartTime(), bookingRequest.getEndTime());
        lastAvailabilityCheck = responseCourtDto;
        return responseCourtDto;
    }

    @Override
    public ResponseCourtDto checkSubCourtStatus(BookingRequest bookingRequest) {
        ResponseCourtDto responseCourtDto = checkCourtAvailability(bookingRequest);
        List<SubCourt> subCourtList = checkRecurring(bookingRequest);
        int count = 0;
        for (SubCourt x : subCourtList) {
            if (!x.isSubCourtStatus()) {
                responseCourtDto.getSubCourt().get(count).setSubCourtStatus(false);
            }
            count++;
        }
        return responseCourtDto;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<SubCourt> checkRecurring(BookingRequest bookingRequest) {
        DayOfWeek day = bookingRequest.getBookingDate().getDayOfWeek();
        RecurringBooking recurringBooking = new RecurringBooking();
        boolean status = false;
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(bookingRequest.getCourtID());
        List<RecurringBooking> booking = recurringBookingRepository.findRecuByCourtID(bookingRequest.getCourtID(), bookingRequest.getStartTime(), bookingRequest.getEndTime());
        for (RecurringBooking x : booking) {
            for (DayOfWeek y : x.getDaysOfWeek()) {
                if (day.equals(y)) {
                    recurringBooking = x;
                    status = true;
                    break;
                }
            }
        }
        if (status) {
            for (SubCourt x : subCourts) {
                for (SubCourt y : recurringBooking.getSubCourts()) {
                    if (x.getSubCourtID().equals(y.getSubCourtID())) x.setSubCourtStatus(false);

                }
            }
        }
        return subCourts;
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
        return calTotalPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime()) * bookingDto.getBookingDetails().size();
    }

    @Override
    public BookingResponseDTO saveBooking(BookingDto bookingDto) {
        //Double tmp = priceRepository.getPriceOfSlot(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getStartTime().plusHours(1));
        Double tmp = getPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getStartTime().plusMinutes(30));
        User user = userRepository.findById(bookingDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Court court = courtRepository.findById(bookingDto.getCourtID())
                .orElseThrow(() -> new RuntimeException("Court not found"));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCourt(court);
        booking.setBooking_type("theo ngay");
        booking.setBookingDate(bookingDto.getBookingDate());
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
        double totalPrice = calTotalPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime()) * bookingDetails.size();
        booking.setTotalPrice(totalPrice);
        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        Booking savedBooking = bookingRepository.save(booking);
        return convertToResponseDTO(savedBooking);
    }


    @Override
    public List<BookingResponse> getBooking(Integer userID) {
        List<Booking> bookingList = bookingRepository.findByUserID(userID);
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingResponse bookingResponse = convertToBookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    public BookingResponse convertToBookingResponse(Booking booking) {

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
        PaymentResDTO paymentResDto = new PaymentResDTO();
        paymentResDto.setBankCode(booking.getPayment().getBankCode());
        paymentResDto.setPaymentAmount(booking.getPayment().getPaymentAmount());
        paymentResDto.setPaymentDate(booking.getPayment().getPaymentTime());
        paymentResDto.setTransactionCode(booking.getPayment().getTransactionCode());

        return BookingResponse.builder()
                .bookingId(booking.getBookingID())
                .courtName(booking.getCourt().getCourtName())
                .address(booking.getCourt().getCourtAddress())
                .courtPhoneNumber(booking.getCourt().getUser().getPhone())
                .customerName(booking.getLastName())
                .customerPhone(booking.getPhone())
                .totalPrice(booking.getTotalPrice())
                .bookingDate(booking.getBookingDate())
                .bookingDetails(detailResponseDTOs)
                .status(booking.isStatus())
                .paymentResDTO(paymentResDto).build();
    }

    @Override
    public List<BookingResponse> getBookingOfCourt(Integer courtID) {
        List<Booking> bookingList = bookingRepository.findByCourtID(courtID);
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingResponse bookingResponse = convertToBookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public Booking createBooking(BookingDto bookingDto) {
        Double tmp = getPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getStartTime().plusMinutes(30));
        User user = userRepository.findById(bookingDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Court court = courtRepository.findById(bookingDto.getCourtID())
                .orElseThrow(() -> new RuntimeException("Court not found"));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCourt(court);
        booking.setBooking_type("theo ngay");
        booking.setFirstName(bookingDto.getFirstName());
        booking.setLastName(bookingDto.getLastName());
        booking.setEmail(bookingDto.getEmail());
        booking.setPhone(bookingDto.getPhone());
        booking.setBookingDate(bookingDto.getBookingDate());
        //booking.setStatus(false);
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
        double totalPrice = calTotalPrice(bookingDto.getCourtID(), bookingDto.getBookingDetails().get(0).getStartTime(), bookingDto.getBookingDetails().get(0).getEndTime()) * bookingDetails.size();
        booking.setTotalPrice(totalPrice);
        return booking;
    }

    public Payment createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        double amount1 = Double.parseDouble(paymentDto.getAmount());
        payment.setPaymentAmount(new BigDecimal(amount1));
        payment.setPaymentTime(new Date());
        payment.setPaymentStatus("Successfully"); // Assuming it's successful by default
        payment.setBankCode(paymentDto.getBankCode());
        payment.setTransactionCode(paymentDto.getTransactionCode());
        return payment;
    }

    @Transactional
    public Booking saveBookingIfUserPaid(BookingPaymentRequest bookingPaymentRequest) {
        BookingRequest bookingRequest = new BookingRequest(bookingPaymentRequest.getBookingDto().getCourtID(), bookingPaymentRequest.getBookingDto().getBookingDate(),
                bookingPaymentRequest.getBookingDto().getBookingDetails().get(0).getStartTime(), bookingPaymentRequest.getBookingDto().getBookingDetails().get(0).getEndTime());
        ResponseCourtDto responseCourtDto = checkCourtAvailability(bookingRequest);
        for (SubCourt subCourt : responseCourtDto.getSubCourt()) {
            int count = 0;
            if (subCourt.getSubCourtID().equals(bookingPaymentRequest.getBookingDto().getBookingDetails().get(count).getSubCourtID()) && !subCourt.isSubCourtStatus()) {
                throw new IllegalStateException("this booking existed");
            }
        }
        //Payment payment = paymentRepository.fin
        if (bookingPaymentRequest.getPaymentDto().getResponseCode().equals("00")) {
            Booking booking = createBooking(bookingPaymentRequest.getBookingDto());
            Double total = booking.getTotalPrice();
            bookingPaymentRequest.getPaymentDto().setAmount(total.toString());
            Payment payment = createPayment(bookingPaymentRequest.getPaymentDto());
            booking.setPayment(payment);
            payment.setBooking(booking);
            return bookingRepository.save(booking);

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
        responseDTO.setBookingDate(booking.getBookingDate());
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

//    @Override
//    public double saveRecureBooking(RecureBooDTO dto) {
//        double totalPrice = 0;
//        double totalSessions = dto.calculateTotalSessions();
//        totalPrice = calTotalPrice(dto.getCourtId(), dto.getStartTime(), dto.getEndTime()) * totalSessions * dto.getListSubCourt().size();
//        bookingDetailsRepository.insertRecurringBooking(
//                dto.getEndDate(),
//                dto.getEndTime(),
//                dto.getStartDate(),
//                dto.getStartTime(),
//                dto.getCourtId(),
//                dto.getUserId(),
//                totalPrice
//        );
//        //bookingDetailsRepository.insertPayment(totalPrice);
//        int recurringBookingId = bookingDetailsRepository.getLastInsertId();
//        for (int subCourtId : dto.getListSubCourt()) {
//            bookingDetailsRepository.insertRecurringBookingSubCourt(recurringBookingId, subCourtId);
//        }
//        for (DayOfWeek bookingDay : dto.getListDayOfWeek()) {
//            //DayOfWeek dayOfWeek = DayOfWeek.valueOf(bookingDay.getDayName().toUpperCase());
//            bookingDetailsRepository.insertRecurringBookingDay(recurringBookingId, bookingDay.toString());
////            bookingDetailsRepository.insertRecurringBookingDay(recurringBookingId, bookingDay.getDayName());
////            totalPrice += calTotalPrice(dto.getCourtId(), dto.getStartTime(), dto.getEndTime());
//        }
//
//        return totalPrice;
//    }

    @Override
    public double saveRecureBooking(RecurringBookingRequest dto) {
        double totalPrice = 0;
        double totalSessions = dto.getRecureBooDTO().calculateTotalSessions();
        totalPrice = calTotalPrice(dto.getRecureBooDTO().getCourtId(), dto.getRecureBooDTO().getStartTime(), dto.getRecureBooDTO().getEndTime()) * totalSessions * dto.getRecureBooDTO().getListSubCourt().size();
        bookingDetailsRepository.insertRecurringBooking(
                dto.getRecureBooDTO().getEndDate(),
                dto.getRecureBooDTO().getEndTime(),
                dto.getRecureBooDTO().getStartDate(),
                dto.getRecureBooDTO().getStartTime(),
                dto.getRecureBooDTO().getCourtId(),
                dto.getRecureBooDTO().getUserId(),
                totalPrice,
                dto.getRecureBooDTO().getFirstName(),
                dto.getRecureBooDTO().getLastName(),
                dto.getRecureBooDTO().getEmail(),
                dto.getRecureBooDTO().getPhone()
        );
        //bookingDetailsRepository.insertPayment(totalPrice);
        int recurringBookingId = bookingDetailsRepository.getLastInsertId();
        for (int subCourtId : dto.getRecureBooDTO().getListSubCourt()) {
            bookingDetailsRepository.insertRecurringBookingSubCourt(recurringBookingId, subCourtId);
        }
        for (DayOfWeek bookingDay : dto.getRecureBooDTO().getListDayOfWeek()) {
            bookingDetailsRepository.insertRecurringBookingDay(recurringBookingId, bookingDay.toString());
        }

        Payment payment = createPayment(dto.getPaymentDTO());
        bookingDetailsRepository.insertPayment(payment.getBankCode(), BigDecimal.valueOf(totalPrice), payment.getPaymentStatus(), payment.getPaymentTime(), payment.getTransactionCode(), recurringBookingId);
        List<LocalDate> dates = new ArrayList<>();
        for (DayOfWeek x : dto.getRecureBooDTO().getListDayOfWeek()) {
            List<LocalDate> date = getDatesForDayOfWeekInRange(dto.getRecureBooDTO().getStartDate(), dto.getRecureBooDTO().getEndDate(), x);
            for (LocalDate bokDate : date) {
                dates.add(bokDate);
            }
        }
        for (LocalDate date : dates) {
            AllBooking booking = AllBooking.builder()
                    .firstName(dto.getRecureBooDTO().getFirstName())
                    .lastName(dto.getRecureBooDTO().getLastName())
                    .email(dto.getRecureBooDTO().getEmail())
                    .phone(dto.getRecureBooDTO().getPhone())
                    .bookingDate(date)
                    .startTime(dto.getRecureBooDTO().getStartTime())
                    .endTime(dto.getRecureBooDTO().getEndTime())
                    .recurringBookingID(recurringBookingId)
                    .userID(dto.getRecureBooDTO().getUserId())
                    .courtID(dto.getRecureBooDTO().getCourtId())
                    .build();
            allBookingRepository.save(booking);

        }
        return totalPrice;
    }

    @Override
    public double getTotalPriceOfRecureBooking(RecurringRequest dto) {
        double totalSessions = dto.calculateTotalSessions();
        return calTotalPrice(dto.getCourtId(), dto.getStartTime(), dto.getEndTime()) * totalSessions * dto.getListSubCourt().size();

    }

    @Override
    public ResponseCourtDto getListAvailableSubCourt(int courId, LocalDate startDate, LocalDate endDate, String dayOfWeek,
                                                     LocalTime startTime, LocalTime endTime) {
        DayOfWeek targetDayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());

        List<LocalDate> dates = getDatesForDayOfWeekInRange(startDate, endDate, targetDayOfWeek);
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(courId);
        ResponseCourtDto responseCourtDto;
        for (LocalDate bokDate : dates) {
            List<BookingDetails> bookingDetails = new ArrayList<>();
            List<Booking> booking = bookingRepository.findByBookingDate(bokDate, courId);
            bookingDetails = bookingDetailsRepository.findExistingTime(startTime, endTime, courId, bokDate);

            for (SubCourt x : subCourts) {
                for (BookingDetails y : bookingDetails) {
                    if (x.getSubCourtID() == y.getSubCourt().getSubCourtID()) {
                        x.setSubCourtStatus(false);
                    }
                }
            }
        }
        List<Integer> listexistSubCourt = bookingDetailsRepository.findSubCourtIds(courId, startDate, endDate, dayOfWeek, startTime, endTime);
        for (Integer subCourtId : listexistSubCourt) {
            for (SubCourt y : subCourts) {
                if (subCourtId == y.getSubCourtID()) {
                    y.setSubCourtStatus(false);
                }
            }
        }
        responseCourtDto = new ResponseCourtDto(courId, subCourts, startDate, startTime, endTime);
        subCourts = new ArrayList<>();
        return responseCourtDto;
    }

    //TODO API CHECK SUB COURT CÒN TRỐNG
    @Override
    public List<SubCourt> checkSubCourtAvailability(int courtId, LocalDate startDate, LocalDate endDate, List<DayOfWeek> daysOfWeek, LocalTime startTime, LocalTime endTime) {
        List<SubCourt> subCourts = subCourtRepository.getSubCourtByCourtID(courtId);
        List<LocalDate> dates = new ArrayList<>();
        for (DayOfWeek x : daysOfWeek) {
            List<LocalDate> date = getDatesForDayOfWeekInRange(startDate, endDate, x);
            for (LocalDate bokDate : date) {
                dates.add(bokDate);
            }
        }
        for (LocalDate bokDate : dates) {
            List<BookingDetails> bookingDetails = bookingDetailsRepository.findExistingTime(startTime, endTime, courtId, bokDate);
            for (SubCourt x : subCourts) {
                for (BookingDetails y : bookingDetails) {
                    if (x.getSubCourtID() == y.getSubCourt().getSubCourtID()) {
                        x.setSubCourtStatus(false);
                    }
                }
            }
        }
        List<RecurringBooking> list = recurringBookingRepository.findRecurringBookingsWithinDateRangeAndTime(courtId, startDate, endDate, startTime, endTime, daysOfWeek.stream().map(DayOfWeek::toString).collect(Collectors.toList()));
        for (SubCourt subCourt1 : subCourts) {
            for (RecurringBooking l : list) {
                if (l.getSubCourts().contains(subCourt1)) {
                    subCourt1.setSubCourtStatus(false);
                }
            }

        }
        return subCourts;
    }

    @Override
    public Booking checkInBooking(int bookingID) {
        Booking booking = bookingRepository.findById(bookingID).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        booking.setStatus(true);
        return bookingRepository.save(booking);
    }

    @Override
    public List<BookingResponse> getBookingForStaff(Integer staffID) {

        Staff staff = staffRepository.findCourtIDByUserID(staffID);
        if (staff == null) {
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        }
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        //List<Booking> list = bookingRepository.findBookingsWithinCurrentWeek(startDate, endDate, staff.getCourt().getCourtID());
        List<Booking> list = bookingRepository.findByCourtID(staff.getCourt().getCourtID());
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : list) {
            BookingResponse bookingResponse = convertToBookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    @Override
    public AllBookingResponse getAllBookingForCourtOnwer(Integer userID) {
        List<BookingDay> bookingDayList = new ArrayList<>();
        List<RecurringBookingDTO> recurringBookingList = new ArrayList<>();
        List<Booking> list = bookingRepository.findByCourtOnwerID(userID);
        List<RecurringBooking> recurringBookings = recurringBookingRepository.findByCourtOnwerID(userID);
        AllBookingResponse allBookingResponse = new AllBookingResponse();
        for (Booking booking : list) {
            BookingDay bookingDay = BookingDay.builder()
                    .bookingID(booking.getBookingID())
                    .courtID(booking.getCourt().getCourtID())
                    .courtName(booking.getCourt().getCourtName())
                    .status(booking.isStatus())
                    .courtOwnerID(booking.getCourt().getUser().getUserID())
                    .bookingDate(booking.getBookingDate())
                    .build();
            bookingDayList.add(bookingDay);
        }
        for (RecurringBooking recurringBooking : recurringBookings) {
            RecurringBookingDTO recurringBookingDTO = RecurringBookingDTO.builder()
                    .recurringBookingID(recurringBooking.getId())
                    .courtID(recurringBooking.getCourt().getCourtID())
                    .courtName(recurringBooking.getCourt().getCourtName())
                    .courtOwnerID(recurringBooking.getUser().getUserID())
                    .bookingDate(recurringBooking.getStartDate())
                    .build();
            recurringBookingList.add(recurringBookingDTO);
        }
        allBookingResponse.setBookingDayList(bookingDayList);
        allBookingResponse.setRecurringBookingList(recurringBookingList);
        return allBookingResponse;
    }

    @Override
    public AllBookingDTO getAllBookingForUser(Integer userID) {
        List<Booking> bookingList = bookingRepository.findByUserID(userID);
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingResponse bookingResponse = convertToBookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        List<RecurringBooking> recurringBookingList = recurringBookingRepository.findBookingOfUser(userID);
        List<RecurringBookingResponse> recurringBookingResponseList = new ArrayList<>();
        for (RecurringBooking recurringBooking : recurringBookingList) {
            RecurringBookingResponse response = convertToRecurringBookingResponse(recurringBooking);
            recurringBookingResponseList.add(response);
        }
        AllBookingDTO allBookingDTO = new AllBookingDTO();
        allBookingDTO.setBookingResponseList(bookingResponseList);
        allBookingDTO.setRecurringBookingResponseList(recurringBookingResponseList);
        return allBookingDTO;
    }
    public RecurringBookingResponse convertToRecurringBookingResponse(RecurringBooking recurring){
        //System.out.println(recurring.getPayment().getPaymentAmount());
        List<String> subCourt = new ArrayList<>();
        for (SubCourt x : recurring.getSubCourts()) {
            subCourt.add(x.getSubCourtName());
        }
        List<String> dayOfWeek = new ArrayList<>();
        for (DayOfWeek x : recurring.getDaysOfWeek()) {
            dayOfWeek.add(x.toString());
        }
        PaymentResDTO paymentResDto = new PaymentResDTO();
        paymentResDto.setBankCode(recurring.getPayment().getBankCode());
        paymentResDto.setPaymentAmount(recurring.getPayment().getPaymentAmount());
        paymentResDto.setPaymentDate(recurring.getPayment().getPaymentTime());
        paymentResDto.setTransactionCode(recurring.getPayment().getTransactionCode());
        return RecurringBookingResponse.builder()
                .recurringBookingID(recurring.getId())
                .courtName(recurring.getCourt().getCourtName())
                .address(recurring.getCourt().getCourtAddress())
                .courtPhoneNumber(recurring.getCourt().getUser().getPhone())
                .customerName(recurring.getLastName())
                .customerPhone(recurring.getPhone())
                .totalPrice(recurring.getTotalPrice())
                .startDate(recurring.getStartDate())
                .endDate(recurring.getEndDate())
                .subCourts(subCourt)
                .daysOfWeek(dayOfWeek)
                .paymentResDTO(paymentResDto)
                .build();
    }
    @Override
    public AllBookingResponse getAllBooking() {
        List<BookingDay> bookingDayList = new ArrayList<>();
        List<RecurringBookingDTO> recurringBookingList = new ArrayList<>();
        List<Booking> list = bookingRepository.findAll();
        List<RecurringBooking> recurringBookings = recurringBookingRepository.findAll();
        AllBookingResponse allBookingResponse = new AllBookingResponse();
        for (Booking booking : list) {
            BookingDay bookingDay = BookingDay.builder()
                    .bookingID(booking.getBookingID())
                    .courtID(booking.getCourt().getCourtID())
                    .courtName(booking.getCourt().getCourtName())
                    .status(booking.isStatus())
                    .courtOwnerID(booking.getCourt().getUser().getUserID())
                    .bookingDate(booking.getBookingDate())
                    .build();
            bookingDayList.add(bookingDay);
        }
        for (RecurringBooking recurringBooking : recurringBookings) {
            RecurringBookingDTO recurringBookingDTO = RecurringBookingDTO.builder()
                    .recurringBookingID(recurringBooking.getId())
                    .courtID(recurringBooking.getCourt().getCourtID())
                    .courtName(recurringBooking.getCourt().getCourtName())
                    .courtOwnerID(recurringBooking.getUser().getUserID())
                    .bookingDate(recurringBooking.getStartDate())
                    .build();
            recurringBookingList.add(recurringBookingDTO);
        }
        allBookingResponse.setBookingDayList(bookingDayList);
        allBookingResponse.setRecurringBookingList(recurringBookingList);
        return allBookingResponse;
    }

    public static List<LocalDate> getDatesForDayOfWeekInRange(LocalDate startDate, LocalDate endDate, DayOfWeek targetDayOfWeek) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = startDate;
        while (current.getDayOfWeek() != targetDayOfWeek) {
            current = current.plusDays(1);
        }
        while (!current.isAfter(endDate)) {
            dates.add(current);
            current = current.plusWeeks(1);
        }
        return dates;
    }

    private double calTotalPrice(int courtID, LocalTime startTime, LocalTime endTime) {
        List<LocalTime> times = new ArrayList<>();
        double sum = 0;
        while (!startTime.isAfter(endTime)) {
            times.add(startTime);
            startTime = startTime.plusMinutes(30);
        }
        for (int i = 0; i < times.size() - 1; i++) {
            //Double tmp = priceRepository.getPriceOfSlot(courtID,times.get(i), times.get(i + 1), dayOfWeek)/2;
            double tmp = getPrice(courtID, times.get(i), times.get(i + 1)) / 2;
            sum += tmp;
        }
        return sum;
    }

    public double getPrice(int courtId, LocalTime startTime, LocalTime endTime) {
//        DayOfWeek dayOfWeek = date.getDayOfWeek();
//        String activeStatus;
//
//        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
//            activeStatus = "CT";
//        } else {
//            activeStatus = "TT";
//        }

        List<Price> priceList = priceRepository.getPriceByCourtID(courtId);
        //("ALL".equals(pricing.getActiveStatus()) || pricing.getActiveStatus().equals(activeStatus)) &&

        for (Price pricing : priceList) {
            if ((startTime.isAfter(pricing.getStartTime()) || startTime.equals(pricing.getStartTime()))
                    && (endTime.isBefore(pricing.getEndTime()) || endTime.equals(pricing.getEndTime()))) {
                return pricing.getUnitPrice();
            }
        }
        throw new IllegalArgumentException("No price found for the given date and time");
    }
}
