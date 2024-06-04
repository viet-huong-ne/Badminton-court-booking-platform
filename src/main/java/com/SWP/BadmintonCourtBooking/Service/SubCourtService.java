package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Entity.SubCourt;

import java.util.List;

public interface SubCourtService {
    List<SubCourt> getSubCourtBySubCourtId(int subCourtId);
}
