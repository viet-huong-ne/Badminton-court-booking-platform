package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Dto.BookingDetailResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringBookingResponse {
    private int recurringBookingID;
    private String courtName;
    private String address;
    private String courtPhoneNumber;
    private String customerName;
    private String customerPhone;
    private double totalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> subCourts;
    private List<String> daysOfWeek;
    private PaymentResDTO paymentResDTO;
}
