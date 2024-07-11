package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Dto.BookingDetailResponseDTO;
import com.SWP.BadmintonCourtBooking.Dto.PaymentDto;
import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private int bookingId;
    private String courtName;
    private String address;
    private String courtPhoneNumber;
    private String customerName;
    private String customerPhone;
    private double totalPrice;
    private boolean status;
    private LocalDate bookingDate;
    private List<BookingDetailResponseDTO> bookingDetails;
    private PaymentResDTO paymentResDTO;
}
