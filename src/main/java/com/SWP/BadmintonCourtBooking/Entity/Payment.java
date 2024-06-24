package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
public class Payment {
    @Id
    @Column(name = "PaymentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @Column(name = "PaymentAmount")
    private BigDecimal paymentAmount;

    @Column(name = "PaymentTime")
    private Date paymentTime;

    @Column(name = "PaymentStatus")
    private String paymentStatus;

//    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "UserID")
    private int userId;

    @Column(name = "BankCode")
    private String bankCode;

    @Column(name = "TrasactionCode")
    private String trasactionCode;

}
