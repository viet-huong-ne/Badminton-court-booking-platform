package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateInforCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateStatusCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Dto.SubCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
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

    @Autowired
    private CourtService courtService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private SubCourtService subCourtService;

    //API-COURT
    //API DÙNG ĐỂ TÌM TẤT CẢ CÁC SÂN
    @GetMapping("/getAllCourt")
    public List<CourtDto> getAllCourt() {
        return courtService.getAllCourt();
    }

//     API de tim kiem cac san theo quan
//    @GetMapping("/{district}")
//    public List<Court> getCourtByDistrict(@PathVariable String district){
//        return courtService.getCourtByDistrict(district);
//    }

    //API DÙNG ĐỂ TÌM SÂN THEO QUẬN
    //TODO SEARCH COURT BY DISTRICT
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
            for (SubCourt s : subCourtList) {
                subCourtDtoList.add(new SubCourtDto(s.getSubCourtID(), s.getSubCourtName(), s.isSubCourtStatus()));
            }
            CourtDto courtDto = CourtDto.builder()
                    .courtID(i.getCourtID())
                    .courtName(i.getCourtName())
                    .district(i.getDistrict())
                    .duration(i.getDuration())
                    .courtAddress(i.getCourtAddress())
                    .courtQuantity(i.getCourtQuantity())
                    .subCourts(subCourtList)
                    .price(i.getPrice())
                    .startTime(i.getStartTime())
                    .endTime(i.getEndTime())
                    .images(i.getImages())
                    .phone(i.getUser().getPhone())
                    .userID(i.getUser().getUserID())
                    .statusCourt(i.getStatusCourt())
                    .serviceCourt(i.getServiceCourt())
                    .build();
            courtDtoList.add(courtDto);
            // slotOfCourtDtoList = new ArrayList<>();
            subCourtDtoList = new ArrayList<>();

        }
        return new ResponseEntity<>(courtDtoList, HttpStatus.OK);
    }


    //API LẤY SÂN THEO ID CHỦ SÂN
    //TODO GET COURT BY USERID
    @GetMapping("/userid/{userID}")
    public ResponseEntity<List<CourtDto>> getCourtByUserID(@PathVariable int userID) {
        return new ResponseEntity<>(courtService.getCourtByUserID(userID),HttpStatus.OK);
    }


    //TODO GET COURT BY ID TO BOOKING
    @GetMapping("/id/{courtID}")
    public ResponseEntity<CourtDto> getCourtByID(@PathVariable Integer courtID) {
        Optional<Court> court = courtService.getCourtByID(courtID);
        if (court.isPresent()) {
            List<Price> price = priceService.getPriceOfCourt(courtID);
            // List<SlotOfCourt> slotOfCourtList = slotOfCourtService.getSlotByID(courtID);
            List<SubCourt> subCourtList = subCourtService.getSubCourtByCourtId(courtID);
            List<SubCourtDto> subCourtDtoList = new ArrayList<>();
            for (SubCourt s : subCourtList) {
                subCourtDtoList.add(new SubCourtDto(s.getSubCourtID(), s.getSubCourtName(), s.isSubCourtStatus()));
            }
            CourtDto courtDto = CourtDto.builder()
                    .courtID(court.get().getCourtID())
                    .duration(court.get().getDuration())
                    .courtName(court.get().getCourtName())
                    .district(court.get().getDistrict())
                    .courtAddress(court.get().getCourtAddress())
                    .courtQuantity(court.get().getCourtQuantity())
                    .subCourts(subCourtList)
                    .price(court.get().getPrice())
                    .startTime(court.get().getStartTime())
                    .endTime(court.get().getEndTime())
                    .images(court.get().getImages())
                    .phone(court.get().getUser().getPhone())
                    .userID(court.get().getUser().getUserID())
                    .statusCourt(court.get().getStatusCourt())
                    .serviceCourt(court.get().getServiceCourt())
                    .build();
            return new ResponseEntity<>(courtDto, HttpStatus.OK);
        } else throw new AppException(ErrorCode.COURT_NOT_FOUND);

    }


    //TODO: CREATE NEW COURT
    @PostMapping("/createcourt")
    public ResponseEntity<CreateCourtResponse> createNewCourt(@RequestBody CreateCourtRequest createCourtRequest) {
        var court = courtService.createNewCourt(createCourtRequest);
        CreateCourtResponse courtDto = CreateCourtResponse.builder()
//                .courtID(court.getCourtID())
                .courtName(court.getCourtName())
                .courtAddress(court.getCourtAddress())
                .district(court.getDistrict())
                .duration(court.getDuration())
                .startTime(court.getStartTime())
                .endTime(court.getEndTime())
                .subCourts(court.getSubCourts())
                .images(court.getImages())
                .userID(court.getUserID())
                .phone(court.getPhone())
                .statusCourt(court.getStatusCourt())
                .serviceCourt(court.getServiceCourt())
                .price(court.getPrice())
                .build();
        return new ResponseEntity<>(courtDto, HttpStatus.OK);
    }


    //TODO: UPDATE STATUS COURT
    @PutMapping("/updatestatuscourt")
    public ResponseEntity<CourtDto> updateStatusCourt(@RequestBody UpdateStatusCourtRequest updateStatusCourtRequest) {
        var courtDTO = courtService.updateStatusCourt(updateStatusCourtRequest);
        return new ResponseEntity<>(courtDTO, HttpStatus.OK);

    }


    //TODO: UPDATE INFOR COURT
    @PutMapping("/updatecourt")
    public ResponseEntity<CourtDto> updateCourt(@RequestBody UpdateInforCourtRequest updateInforCourtRequest) {
        var courtDTO = courtService.updateInforCourt(updateInforCourtRequest);
        return new ResponseEntity<>(courtDTO, HttpStatus.OK);
    }


    //TODO: DELETE COURT
    //@DeleteMapping("/{courtID}")
    @DeleteMapping("/deletecourt")
    public boolean deleteCourt(@RequestBody DeleteCourtRequest deleteCourtRequest) {
        var isSuccess = courtService.deleteCourt(deleteCourtRequest);
        return isSuccess;
    }

}
