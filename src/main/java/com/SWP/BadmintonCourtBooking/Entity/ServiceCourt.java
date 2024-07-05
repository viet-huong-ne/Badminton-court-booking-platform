package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ServiceCourt")
public class ServiceCourt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceID;

    @Column(name = "service_name")
    private String serviceName;

    //@ManyToMany(mappedBy = "serviceCourtSet")
    //private Set<Court> court = new HashSet<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "serviceCourt")
    private List<Court> courts = new ArrayList<>();
}
