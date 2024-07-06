package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ResponseCourtDto {
    private int courtID;
    //private int subcourtId;
    private List<SubCourt> subCourt;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    //private boolean status;
}
