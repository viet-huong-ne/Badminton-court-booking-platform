package com.SWP.BadmintonCourtBooking.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseBooking {
    private int courtID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
