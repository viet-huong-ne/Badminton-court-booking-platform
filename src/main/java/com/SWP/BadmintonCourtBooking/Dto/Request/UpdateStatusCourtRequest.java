package com.SWP.BadmintonCourtBooking.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStatusCourtRequest {
    private Integer courtID;
    private Integer statusCourt;
}
