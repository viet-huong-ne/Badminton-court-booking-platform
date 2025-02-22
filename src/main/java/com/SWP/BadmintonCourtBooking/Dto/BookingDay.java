package com.SWP.BadmintonCourtBooking.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDay {
    private int bookingID;
    private int courtID;
    private String courtName;
    private boolean status;
    private int courtOwnerID;
    private LocalDate bookingDate;

}