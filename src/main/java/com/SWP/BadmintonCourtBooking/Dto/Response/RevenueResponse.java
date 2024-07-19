package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Dto.RevenueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueResponse {
    List<RevenueDTO> revenueDayList;
    List<Double> revenueMonthList;
    double revenueYear;
}
