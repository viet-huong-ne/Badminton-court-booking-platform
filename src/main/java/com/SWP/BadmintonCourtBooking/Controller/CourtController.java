package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Service.CourtService;
import com.SWP.BadmintonCourtBooking.Service.PriceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/court")
public class CourtController {
    @Autowired
    private CourtService courtService;
    @Autowired
    private PriceService priceService;
    @GetMapping("/getAllCourt")
    public List<Court> getAllCourt(){
        return courtService.getAllCourt();
    }
    // API de tim kiem cac san theo quan
//    @GetMapping("/{district}")
//    public List<Court> getCourtByDistrict(@PathVariable String district){
//        return courtService.getCourtByDistrict(district);
//    }

//    @GetMapping("/{district}")
//    public List<CourtDto> getCourtByDistrict(@PathVariable String district){
//        List<Court> court = courtService.getCourtByDistrict(district);
//        List<CourtDto> courtDtoList = new ArrayList<>();
//        for (Court i : court){
//        List<Price> price = priceService.getPriceOfCourt(i.getCourtID());
//        courtDtoList.add(new CourtDto(
//             //   i.getCourtID(),i.getCourtName(),i.getDistrict(),i.getCourtAddress(),i.getCourtQuantity(),i.getDuration(),null,price,null));
//        }
//        return courtDtoList;
//    }
}
//