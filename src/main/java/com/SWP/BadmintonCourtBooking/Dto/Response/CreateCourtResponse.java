package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourtResponse {
//    private String courtName;
//
//    private String district;
//
//    private String courtAddress;
//
//    private LocalTime openTime;
//
//    private LocalTime closeTime;
//
//    private Integer courtQuantity;
//
//    private Integer duration;
//
//    private String images;
    private Court court;

    private List<SubCourt> subCourts;
}
