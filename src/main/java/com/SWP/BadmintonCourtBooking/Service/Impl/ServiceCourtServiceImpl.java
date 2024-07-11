package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewServiceRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteServiceRequest;
import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import com.SWP.BadmintonCourtBooking.Repository.ServiceRepository;
import com.SWP.BadmintonCourtBooking.Service.ServiceCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCourtServiceImpl implements ServiceCourtService {

    @Autowired
    private ServiceRepository serviceRepository;


    @Override
    public List<ServiceCourt> getAllServiceCourt() {
        List<ServiceCourt> serviceCourtList = serviceRepository.findAll();
        return serviceCourtList;
    }

    @Override
    public ServiceCourt addNewServiceCourt(CreateNewServiceRequest createNewServiceRequest) {
        ServiceCourt serviceCourt = new ServiceCourt();
        serviceCourt.setServiceName(createNewServiceRequest.getServiceName());
        serviceRepository.save(serviceCourt);
        return serviceCourt;
    }

    @Override
    public boolean deleteServiceCourt(DeleteServiceRequest deleteServiceRequest) {
        //find service
        var serviceDelete = serviceRepository.findByServiceName(deleteServiceRequest.getServiceName());
        if (serviceDelete != null) {
            System.out.println("Delete Service Successfully");
            serviceRepository.delete(serviceDelete);
            return true;
        }
        System.out.println("Nothing Data To Delete");
        return false;
    }


}
