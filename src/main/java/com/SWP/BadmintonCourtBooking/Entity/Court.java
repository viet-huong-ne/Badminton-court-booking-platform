package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Court")
public class Court {
    @Id
    @Column(name = "court_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courtID;

    @Column(name = "court_name", columnDefinition = "nvarchar(200)")
    private String courtName;

    @Column(name = "district", columnDefinition = "nvarchar(30)")
    private String district;

    @Column(name = "court_address", nullable = false, columnDefinition = "nvarchar(255)")
    private String courtAddress;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "court_quantity")
    private Integer courtQuantity;

    @Column(name = "slot_duration")
    private Integer duration;

    @Column(name = "status_court")
    private Integer statusCourt;

    //@JsonIgnore
    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images;

    //@JsonIgnore
    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCourt> subCourt;

    //@JsonIgnore
    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "court")
    @JsonIgnore
    private List<Booking> booking;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "court_service",
            joinColumns = @JoinColumn(name = "court_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    //private Set<ServiceCourt> serviceCourtSet  = new HashSet<>();
    private List<ServiceCourt> serviceCourt = new ArrayList<>();

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Staff> staff;

}
