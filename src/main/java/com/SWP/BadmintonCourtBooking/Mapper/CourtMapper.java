package com.SWP.BadmintonCourtBooking.Mapper;

import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourtMapper {
    CreateCourtResponse toCreateCourtResponse(Court court);

    //Court toCreateCourtResponsev1(CreateCourtResponse response);
}
