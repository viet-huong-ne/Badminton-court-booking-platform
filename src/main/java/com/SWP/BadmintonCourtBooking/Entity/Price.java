package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "Price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private int priceID;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @Column(name = "opening_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "active_status")
    private String activeStatus;
}
