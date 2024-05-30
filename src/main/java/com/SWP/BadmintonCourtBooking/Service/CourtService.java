package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Entity.Court;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CourtService {
    public List<Court> getAllCourt();
    public List<Court> getCourtByDistrict(String district);
}
