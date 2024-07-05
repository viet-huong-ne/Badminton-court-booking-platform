package com.SWP.BadmintonCourtBooking.Dto.Request;

import com.SWP.BadmintonCourtBooking.Dto.BookingDto;
import com.SWP.BadmintonCourtBooking.Dto.PaymentDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingPaymentRequest {
    private BookingDto bookingDto;
    private PaymentDto paymentDto;

}
