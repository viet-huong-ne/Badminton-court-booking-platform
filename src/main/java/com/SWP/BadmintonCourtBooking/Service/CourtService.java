package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.*;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourtService {

    //TODO:GET ALL COURT
    public List<CourtDto> getAllCourt();

    //TODO: GET COURT BY USERID
    public List<CourtDto> getCourtByUserID(int userID);

    //TODO: GET COURT BY DISTRICT
    public List<Court> getCourtByDistrict(String district);

    //TODO: GET COURT BY ID
    public Optional<Court> getCourtByID(int courtID);

    //TODO: CREATE NEW COURT
    public CreateCourtResponse createNewCourt(CreateCourtRequest createCourtRequest);

    //TODO: UPDATE STATUS COURT
    public CourtDto updateStatusCourt(UpdateStatusCourtRequest updateStatusCourtRequest);

    //TODO: UPDATE INFOR COURT
    public CourtDto updateInforCourt(UpdateInforCourtRequest updateInforCourtRequest);

    //TODO: DELETE COURT
    public boolean deleteCourt(DeleteCourtRequest deleteCourtRequest);

}
