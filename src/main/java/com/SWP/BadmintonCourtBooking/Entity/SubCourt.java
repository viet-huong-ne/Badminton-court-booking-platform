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
    @Column(name = "SubCourtID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer SubCourtID;

    @Column(name = "SubCourtName", columnDefinition = "nvarchar(255)")
    private String SubCourtName;

    @Column(name = "SubCourtStatus")
    private String SubCourtStatus;
    @JsonIgnore
    @OneToMany(mappedBy = "subCourt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingDetails> bookingDetails;
    @ManyToOne
    @JoinColumn(name = "courtID")
    private Court court;
}
