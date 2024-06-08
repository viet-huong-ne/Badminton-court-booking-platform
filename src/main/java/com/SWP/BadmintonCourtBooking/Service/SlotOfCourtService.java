package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.SlotOfCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SlotOfCourt;

import java.util.List;

public interface SlotOfCourtService {
    List<SlotOfCourt> getSlotByID(int id);
}
