package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Time;
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDto {
    private Booking booking;
    private SubCourt subCourt;
    private Integer Quantity;
    private Integer UnitPrice;
    private String ServiceID;
    private Time StartTime;
    private Time EndTime;
}
