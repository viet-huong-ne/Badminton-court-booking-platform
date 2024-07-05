package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateCourtResponse;
import com.SWP.BadmintonCourtBooking.Entity.*;
import com.SWP.BadmintonCourtBooking.Repository.*;
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
    @Autowired
    private ServiceRepository serviceRepository;

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
                .serviceCourt(court.getServiceCourt())
                .build();

    }


    @Override
    public CreateCourtResponse createNewCourt(CreateCourtRequest createCourtRequest) {
        Court court = new Court();
        court.setCourtName(createCourtRequest.getCourtName());
        court.setCourtAddress(createCourtRequest.getCourtAddress());
        court.setDistrict(createCourtRequest.getDistrict());
        court.setDuration(createCourtRequest.getDuration());
        court.setStartTime(createCourtRequest.getStartTime());
        court.setEndTime(createCourtRequest.getEndTime());
        court.setCourtQuantity(createCourtRequest.getCourtQuantity());
        court.setStatusCourt(createCourtRequest.getStatusCourt());

        court.setUser(userRepository.findById(createCourtRequest.getUserID()).orElseThrow(() -> new RuntimeException("User not found")));

        //SubCourt
        List<SubCourt> list = new ArrayList<>();
        for (int i = 0; i < createCourtRequest.getCourtQuantity(); i++) {
            SubCourt subCourt = new SubCourt();
            subCourt.setSubCourtName("Sân " + i + 1);
            subCourt.setCourt(court);
            subCourt.setSubCourtStatus(true);
            list.add(subCourt);
        }
        court.setSubCourt(list);

        //Images
        List<Images> listImages = new ArrayList<>();
        for (int j = 0; j < createCourtRequest.getImages().size(); j++) {
            Images image = new Images();
            image.setCourt(court);
            image.setImage(createCourtRequest.getImages().get(j));
            listImages.add(image);
        }
        court.setImages(listImages);

        //Service
        List<ServiceCourt> listServiceCourts = new ArrayList<>(); //= serviceRepository.findByServiceName(createCourtRequest.getCourtName());
        for (int j = 0; j < createCourtRequest.getServiceCourt().size(); j++) {
            ServiceCourt serviceCourt = new ServiceCourt();
            serviceCourt = serviceRepository.findByServiceName(createCourtRequest.getServiceCourt().get(j));
            listServiceCourts.add(serviceCourt);
        }
        court.setServiceCourt(listServiceCourts);


        //Price
        System.out.println("List: " + createCourtRequest.getPrices());

        List<Price> listPrices = new ArrayList<>();
        for(int i = 0; i < createCourtRequest.getPrices().size(); i++){
            Price price = new Price();
            price.setCourt(court);

            price.setStartTime(createCourtRequest.getPrices().get(i).getStartTime());
            System.out.println("StartTime " + createCourtRequest.getPrices().get(i).getStartTime());

            price.setEndTime(createCourtRequest.getPrices().get(i).getEndTime());
            System.out.println("EndTime " + createCourtRequest.getPrices().get(i).getEndTime());

            price.setUnitPrice(createCourtRequest.getPrices().get(i).getUnitPrice());
            System.out.println("Price " + createCourtRequest.getPrices().get(i).getUnitPrice());

            listPrices.add(price);
        }
        court.setPrice(listPrices);
        System.out.println("ListPrices: " + listPrices);

        courtRepository.save(court);
        return CreateCourtResponse.builder()
                .courtName(court.getCourtName())
                .courtAddress(court.getCourtAddress())
                .district(court.getDistrict())
                .duration(court.getDuration())
                .startTime(court.getStartTime())
                .endTime(court.getEndTime())
                .subCourts(court.getSubCourt())
                .images(court.getImages())
                .userID(court.getUser().getUserID())
                .phone(court.getUser().getPhone())
                .statusCourt(court.getStatusCourt())
                .serviceCourt(court.getServiceCourt())
                .prices(court.getPrice())
                .build();
    }
}
