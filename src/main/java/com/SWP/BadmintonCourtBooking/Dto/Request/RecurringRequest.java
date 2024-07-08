package com.SWP.BadmintonCourtBooking.Dto.Request;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
@Data
public class RecurringRequest {
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    int courtId;
    ArrayList<Integer> listSubCourt;
    ArrayList<DayOfWeek> listDayOfWeek;

    public int calculateTotalSessions() {
        int totalSessions = 0;

        // Iterate through each date between startDate and endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Check if the current date's day of the week is in the listDayOfWeek

            if (listDayOfWeek.contains(date.getDayOfWeek())) {
                totalSessions++;
            }
        }
        return totalSessions;
    }
}
