package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PriceService {
    List<Price> getPriceOfCourt(int courtID);
}
