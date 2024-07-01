package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Mapper.CourtMapper;
import com.SWP.BadmintonCourtBooking.Repository.CourtRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtServiceImpl implements CourtService {
    @Autowired
    private CourtRepository courtRepository;

    private CourtMapper courtMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Court> getAllCourt() {
        return courtRepository.findAll();
    }

    @Override
    public List<Court> getCourtByDistrict(String district) {
        return courtRepository.getCourtByDistrict(district);
    }


    @Override
    public Optional<Court> getCourtByID(int courtID) {
        return courtRepository.findById(courtID);
    }


//    @Override
//    public Court updateCourt(Court courtDetails) {
//        Court court = courtRepository.findById(courtDetails.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));
//        court.setCourtName(courtDetails.getCourtName());
//        court.setCourtAddress(courtDetails.getCourtAddress());
//        court.setCourtQuantity(courtDetails.getCourtQuantity());
//        court.setDuration(courtDetails.getDuration());
//        court.setPrice(courtDetails.getPrice());
//
//        return null;
//    }
//
//
//    @Override
//    public Court createCourt(Court court) {
//        return null;
//    }


    @Override
    public Court createNewCourt(CreateCourtRequest createCourtRequest) {
        Court court = new Court();
        court.setCourtName(createCourtRequest.getCourtName());
        court.setCourtAddress(createCourtRequest.getCourtAddress());
        court.setDistrict(createCourtRequest.getDistrict());
        court.setDuration(createCourtRequest.getDuration());
        court.setOpenTime(createCourtRequest.getOpenTime());
        court.setCloseTime(createCourtRequest.getCloseTime());
        court.setCourtQuantity(createCourtRequest.getCourtQuantity());
        //court.setPrice(createCourtRequest.getListPrice());
        court.setUser(userRepository.findById(createCourtRequest.getUserID()).orElseThrow(() -> new RuntimeException("User not found")));
        List<SubCourt> list = new ArrayList<>();
        for (int i = 0; i < createCourtRequest.getCourtQuantity(); i++) {
            SubCourt subCourt = new SubCourt();
            subCourt.setSubCourtName("Sân " + i);
            subCourt.setCourt(court);
            subCourt.setSubCourtStatus(true);
            list.add(subCourt);
        };
        court.setSubCourt(list);
        //return courtMapper.toCreateCourtResponse(courtRepository.save(court));\
        return  courtRepository.save(court);
    }
}
