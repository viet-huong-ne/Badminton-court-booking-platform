package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecureBooDTO {
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    int courtId;
    int userId;
    ArrayList<Integer> listSubCourt;
    ArrayList<BookingDay> listDayOfWeek;

}
