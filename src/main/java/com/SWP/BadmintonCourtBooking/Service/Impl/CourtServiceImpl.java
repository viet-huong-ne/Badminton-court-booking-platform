package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.CreateCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateInforCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdatePriceCourtRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateStatusCourtRequest;
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
                .district(court.getDistrict())
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
            subCourt.setSubCourtName("Sân " + (i + 1));
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
        //System.out.println("List: " + createCourtRequest.getPrice());
        List<Price> listPrices = new ArrayList<>();
        for(int i = 0; i < createCourtRequest.getPrice().size(); i++){
            Price price = new Price();
            price.setCourt(court);

            price.setStartTime(createCourtRequest.getPrice().get(i).getStartTime());
            System.out.println("StartTime " + createCourtRequest.getPrice().get(i).getStartTime());

            price.setEndTime(createCourtRequest.getPrice().get(i).getEndTime());
            System.out.println("EndTime " + createCourtRequest.getPrice().get(i).getEndTime());

            price.setUnitPrice(createCourtRequest.getPrice().get(i).getUnitPrice());
            System.out.println("Price " + createCourtRequest.getPrice().get(i).getUnitPrice());

            listPrices.add(price);
        }
        court.setPrice(listPrices);
        //  System.out.println("ListPrices: " + listPrices);

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
                .price(court.getPrice())
                .build();
    }

    @Override
    public CourtDto updateStatusCourt(UpdateStatusCourtRequest updateStatusCourtRequest) {
        //tìm ra cái sân
        Court court = courtRepository.findById(updateStatusCourtRequest.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));
        System.out.println("id: " + court);
        int status = updateStatusCourtRequest.getStatusCourt();
        if (status > 1 || status < -1) {
            throw new RuntimeException("Status court not found");
        }
        court.setStatusCourt(status);
        courtRepository.save(court);
        CourtDto courtDto = convertToDto(court);
        System.out.println("CourtDto after update: " + courtDto.toString());
        return courtDto;
    }



    @Override
    public CourtDto updatePriceCourt(UpdatePriceCourtRequest updatePriceCourtRequest) {
        //tìm ra cái sân
        Court existCourt = courtRepository.findById(updatePriceCourtRequest.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));

        //Update infor sân


        //Lấy ra list price danh sách cũ
        List<Price> listExistPrice = existCourt.getPrice();
        if(existCourt.getStatusCourt() != 1){
            throw new RuntimeException("Court can not be updated");
        }

        if(updatePriceCourtRequest.getPrice().isEmpty()){
            System.out.println("List rỗng");
            existCourt.setPrice(listExistPrice);
        }else{
            List<Price> listReqPrice = updatePriceCourtRequest.getPrice();
            for(int i = 0; i < listReqPrice.size(); i++){
                listExistPrice.get(i).setStartTime(listReqPrice.get(i).getStartTime());
                listExistPrice.get(i).setEndTime(listReqPrice.get(i).getEndTime());
                listExistPrice.get(i).setUnitPrice(listReqPrice.get(i).getUnitPrice());
            }
            existCourt.setPrice(listExistPrice);
            courtRepository.save(existCourt);
        }
        CourtDto courtDto = convertToDto(existCourt);
        return courtDto;
    }

    @Override
    public CourtDto updateInforCourt(UpdateInforCourtRequest updateInforCourtRequest) {
        return null;
    }


}
