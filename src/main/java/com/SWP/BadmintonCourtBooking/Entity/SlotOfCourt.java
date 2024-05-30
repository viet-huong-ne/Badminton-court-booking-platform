package com.SWP.BadmintonCourtBooking.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Entity
@Table(name = "SlotOfCourt")
@Data
public class SlotOfCourt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SlotID")
    private Integer slotID;

    @ManyToOne
    @JoinColumn(name = "CourtID", nullable = false)
    private Court court;

    @Column(name = "opening_time")
    private Time openTime;

    @Column(name = "close_time")
    private Time closeTime;

    @Column(name = "activeStatus")
    private String activeStatus;
}
