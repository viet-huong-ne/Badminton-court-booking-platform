package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.SubCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Repository.SubCourtRepository;
import com.SWP.BadmintonCourtBooking.Service.SubCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubCourtServiceImpl implements SubCourtService {
    @Autowired
    private SubCourtRepository subCourtRepository;

    @Override
    public List<SubCourt> getSubCourtByCourtId(int courtID) {
        return subCourtRepository.getSubCourtByCourtID(courtID);
    }



}
