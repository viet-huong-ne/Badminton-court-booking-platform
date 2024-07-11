package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.CourtDto;
import com.SWP.BadmintonCourtBooking.Dto.Request.*;
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
        List<ServiceCourt> listServiceCourts = new ArrayList<>();
        for (int j = 0; j < createCourtRequest.getServiceCourt().size(); j++) {
            ServiceCourt serviceCourt = new ServiceCourt();
            serviceCourt = serviceRepository.findByServiceName(createCourtRequest.getServiceCourt().get(j));
            listServiceCourts.add(serviceCourt);
        }
        court.setServiceCourt(listServiceCourts);


        //Price
        List<Price> listPrices = new ArrayList<>();
        for (int i = 0; i < createCourtRequest.getPrice().size(); i++) {
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
    public CourtDto updateInforCourt(UpdateInforCourtRequest updateInforCourtRequest) {
        //tìm ra cái sân
        Court existCourt = courtRepository.findById(updateInforCourtRequest.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));

        //Update infor sân
        existCourt.setCourtName(updateInforCourtRequest.getCourtName());
        existCourt.setDistrict(updateInforCourtRequest.getDistrict());
        existCourt.setCourtAddress(updateInforCourtRequest.getCourtAddress());
        existCourt.setDuration(updateInforCourtRequest.getDuration());


        //update sub sân - lấy ra list cũ
        int sizeListReqSubCourt = updateInforCourtRequest.getCourtQuantity();
        System.out.println("Size list sub new: " + sizeListReqSubCourt);
        List<SubCourt> listExistSubCourt = existCourt.getSubCourt();
        System.out.println("Size list sub old: " + listExistSubCourt.size());
        if(listExistSubCourt.size() != sizeListReqSubCourt){
            //xóa hết các subcourt
            existCourt.getSubCourt().clear();
            System.out.println("ExistCourt: " + existCourt.getSubCourt());
            for(int i = 0; i < sizeListReqSubCourt; i++){
                SubCourt subCourt = new SubCourt();
                subCourt.setCourt(existCourt);
                subCourt.setSubCourtName("" + (i + 1));
                subCourt.setSubCourtStatus(true);
                listExistSubCourt.add(subCourt);
            }
            System.out.println("List: " + listExistSubCourt);
            existCourt.setSubCourt(listExistSubCourt);
        } else {
            //Không thay đổi
            System.out.println("Không thay đổi gì về subCourt");
        }
        System.out.println("Update subcourt thành công");
        existCourt.setSubCourt(listExistSubCourt);


        //update ảnh
        List<String> imagesReq = updateInforCourtRequest.getImages();
        System.out.println("list images req: " + imagesReq.toString());
        List<Images> imagesDB = existCourt.getImages();
        System.out.println("list images DB: " + imagesDB.toString());
        if(!imagesReq.isEmpty()) {
            //xóa đi các ảnh cũ
            imagesDB.clear();
            //add ảnh
            for (int j = 0; j < imagesReq.size(); j++) {
                Images image = new Images();
                image.setCourt(existCourt);
                image.setImage(updateInforCourtRequest.getImages().get(j));
                imagesDB.add(image);
            }
        } else {
            System.out.println("Không thay đổi gì về images");
        }
        System.out.println("Update images thành công");
        existCourt.setImages(imagesDB);




        //update service
        List<ServiceCourt> listServiceCourtDB = existCourt.getServiceCourt();
        System.out.println("Service_DB: " + listServiceCourtDB.toString());
        List<String> listServiceCourt = updateInforCourtRequest.getServiceCourt();
        System.out.println("Service_REQ: " + listServiceCourt.toString());
        if(!listServiceCourtDB.isEmpty()) {
            //lấy ra cái service cũ
            existCourt.getServiceCourt().clear();
            System.out.println("List Services DB: " + existCourt.getServiceCourt());
            for (int j = 0; j < listServiceCourt.size(); j++) {
                ServiceCourt serviceCourt = new ServiceCourt();
                serviceCourt = serviceRepository.findByServiceName(updateInforCourtRequest.getServiceCourt().get(j));
                listServiceCourtDB.add(serviceCourt);
            }
        } else{
            System.out.println("Không thay đổi Services");
        }
        System.out.println("Update Services thành công");
        existCourt.setServiceCourt(listServiceCourtDB);



        //Udpate Price
        List<Price> listExistPrice = existCourt.getPrice(); //Lấy ra list price danh sách cũ
        if (existCourt.getStatusCourt() == -1) {
            throw new RuntimeException("Court can not be updated");
        }
        if (updateInforCourtRequest.getPrice().isEmpty()) {
            System.out.println("List rỗng");
            existCourt.setPrice(listExistPrice);
        } else {
            List<Price> listReqPrice = updateInforCourtRequest.getPrice();
            System.out.println("Update tới đây" + listReqPrice.size());
            for (int i = 0; i < listReqPrice.size(); i++) {
                listExistPrice.get(i).setStartTime(listReqPrice.get(i).getStartTime());
                listExistPrice.get(i).setEndTime(listReqPrice.get(i).getEndTime());
                listExistPrice.get(i).setUnitPrice(listReqPrice.get(i).getUnitPrice());
            }
            existCourt.setPrice(listExistPrice);
        }
        courtRepository.save(existCourt);
        CourtDto courtDto = convertToDto(existCourt);
        return courtDto;
    }





    @Override
    public boolean deleteCourt(DeleteCourtRequest deleteCourtRequest) {
        //tìm ra cái sân
        Court existCourt = courtRepository.findById(deleteCourtRequest.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));
        existCourt.getSubCourt().clear();
        existCourt.getServiceCourt().clear();
        existCourt.getPrice().clear();
        existCourt.getImages().clear();
        courtRepository.save(existCourt);
        courtRepository.delete(existCourt);
        System.out.println("Delete court successfully");
        return true;
    }




}
