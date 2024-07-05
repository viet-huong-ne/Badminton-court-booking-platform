package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourtService {

    //TODO:
    public List<CourtDto> getAllCourt();
    //TODO: GET COURT BY USERID
    public List<CourtDto> getCourtByUserID(int userID);
    //TODO:
    public List<Court> getCourtByDistrict(String district);

    //TODO:
    public Optional<Court> getCourtByID(int courtID);

    //TODO: GET ALL COURT
    public List<CourtDto> getAllCourtV1();

    //TODO: CREATE NEW COURT
    public CreateCourtResponse createNewCourt(CreateCourtRequest createCourtRequest);
}
