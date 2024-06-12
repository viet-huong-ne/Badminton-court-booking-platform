package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseCourtDto {
    private int courtID;
    //private int subcourtId;
    private List<SubCourt> subCourt;
    private Date bookingDate;
    private Time startTime;
    private Time endTime;
    //private boolean status;
}
