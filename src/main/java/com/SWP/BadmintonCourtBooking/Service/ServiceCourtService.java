package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewServiceRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteServiceRequest;
import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceCourtService {

    //TODO: GET ALL SERVICE
    List<ServiceCourt> getAllServiceCourt();

    //TODO: ADD NEW SERVICE
    ServiceCourt addNewServiceCourt(CreateNewServiceRequest createNewServiceRequest);


    //TODO: DELETE SERVICE
    boolean deleteServiceCourt(DeleteServiceRequest deleteServiceRequest);

}
