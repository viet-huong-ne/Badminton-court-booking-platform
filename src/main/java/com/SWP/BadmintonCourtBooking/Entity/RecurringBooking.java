package com.SWP.BadmintonCourtBooking.Entity;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class RecurringBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "recurring_booking_sub_courts",
            joinColumns = @JoinColumn(name = "recurring_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_court_id"))
    private List<SubCourt> subCourts;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "recurring_booking_days", joinColumns = @JoinColumn(name = "recurring_booking_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
