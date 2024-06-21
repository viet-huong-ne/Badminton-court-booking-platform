package com.SWP.BadmintonCourtBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCourtDto {
    private int subCourtId;
    private String subCourtName;
    private boolean subCourtStatus;
    //private boolean status;
}
