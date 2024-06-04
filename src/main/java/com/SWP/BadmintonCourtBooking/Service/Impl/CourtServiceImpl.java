package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Repository.CourtRepository;
import com.SWP.BadmintonCourtBooking.Service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourtServiceImpl implements CourtService {
    @Autowired
    private CourtRepository courtRepository;
    @Override
    public List<Court> getAllCourt() {
        return courtRepository.findAll();
    }

    @Override
    public List<Court> getCourtByDistrict(String district) {
        return courtRepository.getCourtByDistrict(district);
    }

    @Override
    public Court updateCourt(Court courtDetails) {
        Court court = courtRepository.findById(courtDetails.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));
        court.setCourtName(courtDetails.getCourtName());
        court.setCourtAddress(courtDetails.getCourtAddress());
        court.setCourtQuantity(courtDetails.getCourtQuantity());
        court.setDuration(courtDetails.getDuration());
        court.setPrice(courtDetails.getPrice());

        return null;
    }

    @Override
    public Court createCourt(Court court) {
        return null;
    }
}
