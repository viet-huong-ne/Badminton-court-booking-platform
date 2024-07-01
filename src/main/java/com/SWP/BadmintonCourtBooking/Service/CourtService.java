package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourtService {

    public List<Court> getAllCourt();


    public List<Court> getCourtByDistrict(String district);


    public Optional<Court> getCourtByID(int courtID);


//    public Court createCourt(Court court);
//
//
//    public Court updateCourt(Court court);

    //TODO: CREATE NEW COURT
    public Court createNewCourt(CreateCourtRequest createCourtRequest);
}
