package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourtResponse {

    private Court court;
    /*
    private String courtName;

    private String district;

    private String courtAddress;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer duration;

    private List<Images> images;

    private Integer courtQuantity;
    */
    //private List<SubCourt> subCourts;
}
