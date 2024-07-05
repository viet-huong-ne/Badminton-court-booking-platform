package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BookingID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "booking_type", columnDefinition = "nvarchar(255)")
    private String booking_type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<BookingDetails> bookingDetails;
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;
}
