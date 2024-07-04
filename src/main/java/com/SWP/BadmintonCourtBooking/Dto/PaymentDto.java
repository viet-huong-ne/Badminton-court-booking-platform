package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private String amount;
    private String bankCode;
    private String responseCode;
    private String transactionCode;
}
