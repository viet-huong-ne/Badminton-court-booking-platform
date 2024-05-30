package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Entity
@Data
@Table(name = "Price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priceID")
    private int priceID;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "courtID", nullable = false)
    private Court court;

    @Column(name = "opening_time")
    private Time openTime;

    @Column(name = "close_time")
    private Time closeTime;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "activeStatus")
    private String activeStatus;
}
