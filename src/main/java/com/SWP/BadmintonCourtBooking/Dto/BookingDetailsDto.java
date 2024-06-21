package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDetailsDto {
    private int subCourtID;
    //private double unitPrice;
    private LocalTime StartTime;
    private LocalTime EndTime;
}
