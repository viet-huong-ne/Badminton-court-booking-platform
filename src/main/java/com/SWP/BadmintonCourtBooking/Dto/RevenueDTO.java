package com.SWP.BadmintonCourtBooking.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
public class RevenueDTO {
    private Date revenueDate;
    private double amount;
    public RevenueDTO() {}

    // Constructor with parameters
    public RevenueDTO(Date revenueDate, double amount) {
        this.revenueDate = revenueDate;
        this.amount = amount;
    }

    // Getters and setters
    public Date getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
