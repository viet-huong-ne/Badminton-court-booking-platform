package com.SWP.BadmintonCourtBooking.Dto.Request;

import com.SWP.BadmintonCourtBooking.Dto.PaymentDto;
import com.SWP.BadmintonCourtBooking.Dto.RecureBooDTO;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
public class RecurringBookingRequest {
    private RecureBooDTO recureBooDTO;
    private PaymentDto paymentDTO;
}
