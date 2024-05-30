package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Repository.PriceRepository;
import com.SWP.BadmintonCourtBooking.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    PriceRepository priceRepository;
    @Override
    public List<Price> getPriceOfCourt(int courtID) {

        return priceRepository.getPriceByCourtID(courtID);
    }
}
