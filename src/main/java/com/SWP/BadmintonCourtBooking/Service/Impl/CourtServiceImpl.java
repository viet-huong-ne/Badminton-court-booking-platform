package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Dto.SubCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Repository.CourtRepository;
import com.SWP.BadmintonCourtBooking.Repository.PriceRepository;
import com.SWP.BadmintonCourtBooking.Repository.SubCourtRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtServiceImpl implements CourtService {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private SubCourtRepository subCourtRepository;

    @Override
    public List<CourtDto> getAllCourt() {
        List<Court> courts = courtRepository.findAll();
        List<CourtDto> courtDtos = new ArrayList<>();
        for (Court court : courts) {
            courtDtos.add(convertToDto(court));

        }
        return courtDtos;
    }

    @Override
    public List<CourtDto> getCourtByUserID(int userID) {
        List<Court> courts = courtRepository.getCourtByUserID(userID);
        List<CourtDto> courtDtos = new ArrayList<>();
        for (Court court : courts) {
            CourtDto courtDto = convertToDto(court);
            courtDtos.add(courtDto);
        }
        return courtDtos;
    }

    @Override
    public List<Court> getCourtByDistrict(String district) {
        return courtRepository.getCourtByDistrict(district);
    }


    @Override
    public Optional<Court> getCourtByID(int courtID) {
        return courtRepository.findById(courtID);
    }

//    @Override
//    public List<CourtDto> getAllCourtV1() {
//        List<CourtDto> listCourtDTO = new ArrayList<>();
//        List<Court> listCourts = courtRepository.findAll();
//        for (Court court : listCourts) {
//            listCourtDTO.add(convertToDto(court));
//        }
//        return listCourtDTO;
//    }

    public CourtDto convertToDto(Court court) {
        return CourtDto.builder()
                .courtID(court.getCourtID())
                .courtName(court.getCourtName())
                .courtAddress(court.getCourtAddress())
                .District(court.getDistrict())
                .duration(court.getDuration())
                .startTime(court.getStartTime())
                .endTime(court.getEndTime())
                .courtQuantity(court.getCourtQuantity())
                .images(court.getImages())
                .subCourts(court.getSubCourt())
                .price(court.getPrice())
                .userID(court.getUser().getUserID())
                .phone(court.getUser().getPhone())
                .statusCourt(court.getStatusCourt())
                .build();

    }


    @Override
    public CreateCourtResponse createNewCourt(CreateCourtRequest createCourtRequest) {
        Court court = new Court();
        court.setCourtName(createCourtRequest.getCourtName());
        court.setCourtAddress(createCourtRequest.getCourtAddress());
        court.setDistrict(createCourtRequest.getDistrict());
        court.setDuration(createCourtRequest.getDuration());
        court.setEndTime(createCourtRequest.getOpenTime());
        court.setEndTime(createCourtRequest.getCloseTime());
        court.setCourtQuantity(createCourtRequest.getCourtQuantity());
        court.setStatusCourt(createCourtRequest.getStatusCourt());
        court.setUser(userRepository.findById(createCourtRequest.getUserID()).orElseThrow(() -> new RuntimeException("User not found")));

        List<SubCourt> list = new ArrayList<>();
        for (int i = 0; i < createCourtRequest.getCourtQuantity(); i++) {
            SubCourt subCourt = new SubCourt();
            subCourt.setSubCourtName("Sân " + i + 1);
            subCourt.setCourt(court);
            subCourt.setSubCourtStatus(true);
            list.add(subCourt);
        }
        court.setSubCourt(list);

        List<Images> listImages = new ArrayList<>();
        for (int j = 0; j < createCourtRequest.getImages().size(); j++) {
            Images image = new Images();

            image.setCourt(court);
            image.setImage(createCourtRequest.getImages().get(j));
            listImages.add(image);
        }
        court.setImages(listImages);

        courtRepository.save(court);
        return CreateCourtResponse.builder()
                .courtName(court.getCourtName())
                .courtAddress(court.getCourtAddress())
                .district(court.getDistrict())
                .duration(court.getDuration())
                .openTime(court.getStartTime())
                .closeTime(court.getEndTime())
                .subCourts(court.getSubCourt())
                .images(court.getImages())
                .userID(court.getUser().getUserID())
                .phone(court.getUser().getPhone())
                .build();
    }
}
