package com.SWP.BadmintonCourtBooking.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Staff {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "court_id")
    private Integer courtID;
}