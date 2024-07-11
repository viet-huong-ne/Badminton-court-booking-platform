package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewServiceRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteServiceRequest;
import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import com.SWP.BadmintonCourtBooking.Repository.ServiceRepository;
import com.SWP.BadmintonCourtBooking.Service.ServiceCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceCourtService serviceCourtService;

    //TODO: GET ALL SERVICE
    @GetMapping("/getallservice")
    public ResponseEntity<List<ServiceCourt>> getAllService(){
        List<ServiceCourt> listService = serviceCourtService.getAllServiceCourt();
        return new ResponseEntity<>(listService, HttpStatus.OK);
    }


    //TODO: CREATE NEW SERVICE
    @PostMapping("/createservice")
    public ResponseEntity<ServiceCourt> createServiceCourt(@RequestBody CreateNewServiceRequest createNewServiceRequest) {
        var service = serviceCourtService.addNewServiceCourt(createNewServiceRequest);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }


    //TODO: DELETE SERVICE
    @DeleteMapping("/deleteservice")
    public boolean deleteService(@RequestBody DeleteServiceRequest deleteServiceRequest) {
        var isSuccess = serviceCourtService.deleteServiceCourt(deleteServiceRequest);
        return isSuccess;
    }
}
