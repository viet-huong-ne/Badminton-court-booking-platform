package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

//@Getter
//@Setter
@Data
@Entity
@Table(name = "Court")
@NoArgsConstructor
@AllArgsConstructor
public class Court {
    @Id
    @Column(name = "CourtID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  courtID;

    @Column(name = "CourtName", columnDefinition = "nvarchar(200)")
    private String courtName;

    @Column(name = "District", columnDefinition = "nvarchar(30)")
    private String District;

    @Column(name = "court_address", nullable = false, columnDefinition = "nvarchar(255)")
    private String courtAddress;

    @Column(name = "court_quantity")
    private Integer  courtQuantity;

    @Column(name = "slot_duration")
    private Integer  duration;

    @Column(name = "Images")
    private String images;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCourt> subCourt;
    //@JsonIgnore
    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> price;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL)
    private List<SlotOfCourt> slotOfCourt;
}
