package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "payment_time")
    private Date paymentTime;

    @Column(name = "payment_status")
    private String paymentStatus;

//    @ManyToOne
//    @JsonIgnore
    //@JoinColumn(name = "user_id")
    //private int userId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "transaction_code")
    private String transactionCode;

}
