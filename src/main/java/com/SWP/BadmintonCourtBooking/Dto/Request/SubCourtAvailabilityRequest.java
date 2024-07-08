package com.SWP.BadmintonCourtBooking.Dto.Request;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class SubCourtAvailabilityRequest {
    private int courtId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
