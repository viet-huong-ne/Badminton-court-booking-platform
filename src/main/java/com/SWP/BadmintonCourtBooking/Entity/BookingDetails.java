package com.SWP.BadmintonCourtBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

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
    private Integer Quantity;

    @Column(name = "UnitPrice")
    private Integer UnitPrice;

    @Column(name = "ServiceID")
    private String ServiceID;

    @Column(name = "StartTime")
    //private LocalDate StartTime;
    private Time StartTime;

    @Column(name = "EndTime")
    //private LocalDate EndTime;
    private Time EndTime;
}
