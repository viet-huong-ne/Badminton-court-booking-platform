package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "SubCourt")
public class SubCourt {
    @Id
    @Column(name = "sub_court_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer SubCourtID;

    @Column(name = "sub_court_name", columnDefinition = "nvarchar(255)")
    private String SubCourtName;

    @Column(name = "sub_court_status")
    private boolean SubCourtStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "subCourt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingDetails> bookingDetails;

    @ManyToMany(mappedBy = "subCourts")
    @JsonIgnore
    private List<RecurringBooking> recurringBookings;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;
}
