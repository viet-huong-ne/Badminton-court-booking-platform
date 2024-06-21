package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "BookingID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BookingID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CourtID", nullable = false)
    private Court court;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "booking_date")
    private LocalDate booking_date;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @Column(name = "booking_type", columnDefinition = "nvarchar(255)")
    private String booking_type;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BookingDetails> bookingDetails;
}
