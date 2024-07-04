package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResDTO implements Serializable {
//    private String status;
//    private String message;
//    private String URL;

    //    public String getStatus() {
//        return status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getURL() {
//        return URL;
//    }
//
//    public void setURL(String URL) {
//        this.URL = URL;
//    }
    private String bankCode;
    private BigDecimal paymentAmount;
    private Date paymentDate;
    private String transactionCode;
}

