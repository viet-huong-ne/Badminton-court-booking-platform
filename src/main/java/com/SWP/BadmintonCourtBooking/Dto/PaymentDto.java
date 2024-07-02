package com.SWP.BadmintonCourtBooking.Dto;

import lombok.Data;

@Data
public class PaymentDto {
    private String amount;
    private String bankCode;
    private String responseCode;
    private String transactinCode;
}
