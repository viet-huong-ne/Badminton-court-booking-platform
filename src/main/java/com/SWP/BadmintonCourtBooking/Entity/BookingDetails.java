package com.SWP.BadmintonCourtBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "BookingDetails")
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "SubCourtID")
    private SubCourt subCourt;

    @Column(name = "Quantity")
    private int Quantity;

    @Column(name = "UnitPrice")
    private Double UnitPrice;

//    @Column(name = "ServiceID")
//    private String ServiceID;

    @Column(name = "StartTime")
    private LocalTime StartTime;
    //private Time StartTime;

    @Column(name = "EndTime")
    private LocalTime EndTime;
    //private Time EndTime;
}
