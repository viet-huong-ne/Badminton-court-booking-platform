package com.SWP.BadmintonCourtBooking.Dto.Request;

import com.SWP.BadmintonCourtBooking.Entity.SubCourt;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RecurringBookingRequest {

    private int userId;

    private List<Integer> subCourts;

    private List<DayOfWeek> daysOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
