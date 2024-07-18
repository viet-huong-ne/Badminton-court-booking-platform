package com.SWP.BadmintonCourtBooking.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringBookingDTO {
    private int recurringBookingID;
    private int courtID;
    private String courtName;
    private int courtOwnerID;
    private LocalDateTime bookingDate;
}
