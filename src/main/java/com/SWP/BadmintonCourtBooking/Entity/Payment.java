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

    @Column(name = "BookInfo")
    private String bookInfo;

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    public String getBookInfo() {
        return bookInfo;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

//    @JsonIgnore
//    public User getUser() {
//        return user;
//    }

//    public Integer getUserId() {
//        return user != null ? user.getId() : null;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
