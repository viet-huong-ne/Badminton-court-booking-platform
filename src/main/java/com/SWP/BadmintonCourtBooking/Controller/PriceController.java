package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price")
@CrossOrigin("*")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/id/{courtID}")
    public List<Price> getPriceByID(@PathVariable Integer courtID){
        return priceService.getPriceOfCourt(courtID);
    }
}
