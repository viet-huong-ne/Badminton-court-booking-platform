package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SubCourt")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;
}
