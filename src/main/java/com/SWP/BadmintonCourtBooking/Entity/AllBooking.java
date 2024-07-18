package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllBooking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    private LocalTime startTime;
    private LocalTime endTime;

    @JoinColumn(name = "recurring_booking_id")
    private int recurringBookingID;

    @Column(name = "status")
    private boolean status;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "court_id")
    private Integer courtID;

}
