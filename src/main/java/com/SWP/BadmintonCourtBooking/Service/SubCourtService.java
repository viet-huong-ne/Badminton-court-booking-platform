package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.SubCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SubCourtService {
    List<SubCourt> getSubCourtByCourtId(int courtId);


}
