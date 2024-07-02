package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.SlotOfCourtDto;
import com.SWP.BadmintonCourtBooking.Dto.SubCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Service.CourtService;
import com.SWP.BadmintonCourtBooking.Service.PriceService;
import com.SWP.BadmintonCourtBooking.Service.SubCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/court")
public class CourtController {
    //private static final Logger logger = LoggerFactory.getLogger(CourtController.class);
    @Autowired
    private CourtService courtService;
    @Autowired
    private PriceService priceService;

    @Autowired
    private SubCourtService subCourtService;
    //API DÙNG ĐỂ TÌM TẤT CẢ CÁC SÂN
    @GetMapping("/getAllCourt")
    public List<Court> getAllCourt() {
        return courtService.getAllCourt();
    }
//     API de tim kiem cac san theo quan
//    @GetMapping("/{district}")
//    public List<Court> getCourtByDistrict(@PathVariable String district){
//        return courtService.getCourtByDistrict(district);
//    }
    //API DÙNG ĐỂ TÌM SÂN THEO QUẬN
    @GetMapping("/{district}")
    public ResponseEntity<List<CourtDto>> getCourtByDistrict(@PathVariable String district) {
        List<Court> court = courtService.getCourtByDistrict(district);
        List<CourtDto> courtDtoList = new ArrayList<>();
       // List<SlotOfCourtDto> slotOfCourtDtoList = new ArrayList<>();
        List<SubCourtDto> subCourtDtoList = new ArrayList<>();
        for (Court i : court) {

            List<Price> price = priceService.getPriceOfCourt(i.getCourtID());
           // List<SlotOfCourt> slotOfCourtList = slotOfCourtService.getSlotByID(i.getCourtID());
            List<SubCourt> subCourtList = subCourtService.getSubCourtByCourtId(i.getCourtID());

//            for (SlotOfCourt s : slotOfCourtList) {
//
//                slotOfCourtDtoList.add(new SlotOfCourtDto(s.getOpenTime(), s.getCloseTime(), s.getActiveStatus()));
//            }

            for (SubCourt s : subCourtList) {

                subCourtDtoList.add(new SubCourtDto(s.getSubCourtID(),s.getSubCourtName(), s.isSubCourtStatus()));
            }

            courtDtoList.add(new CourtDto(
                    i.getCourtID(), i.getCourtName(), i.getDistrict(), i.getCourtAddress(), i.getCourtQuantity(), i.getDuration(),i.getImages(), subCourtDtoList, price, i.getOpenTime(), i.getCloseTime()));
           // slotOfCourtDtoList = new ArrayList<>();
            subCourtDtoList = new ArrayList<>();

        }
        return new ResponseEntity<>(courtDtoList, HttpStatus.OK);
    }
    //TODO GET COURT BY ID TO BOOKING
    @GetMapping("/id/{courtID}")
    public ResponseEntity<CourtDto> getCourtByID(@PathVariable Integer courtID) {
        Optional<Court> court = courtService.getCourtByID(courtID);
        if (court.isPresent()) {
            List<Price> price = priceService.getPriceOfCourt(courtID);
           // List<SlotOfCourt> slotOfCourtList = slotOfCourtService.getSlotByID(courtID);
            List<SubCourt> subCourtList = subCourtService.getSubCourtByCourtId(courtID);
            List<SlotOfCourtDto> slotOfCourtDtoList = new ArrayList<>();
            List<SubCourtDto> subCourtDtoList = new ArrayList<>();
//            for (SlotOfCourt s : slotOfCourtList) {
//                slotOfCourtDtoList.add(new SlotOfCourtDto(s.getOpenTime(), s.getCloseTime(), s.getActiveStatus()));
//            }

            for (SubCourt s : subCourtList) {
                subCourtDtoList.add(new SubCourtDto(s.getSubCourtID(),s.getSubCourtName(), s.isSubCourtStatus()));
            }

            return new ResponseEntity<>(new CourtDto(court.get().getCourtID(), court.get().getCourtName(), court.get().getDistrict(), court.get().getCourtAddress(), court.get().getCourtQuantity(), court.get().getDuration(),court.get().getImages(), subCourtDtoList, price, court.get().getOpenTime(), court.get().getCloseTime()),HttpStatus.OK);
        } else throw new IllegalArgumentException("Court not found for ID: " + courtID);

    }
}
