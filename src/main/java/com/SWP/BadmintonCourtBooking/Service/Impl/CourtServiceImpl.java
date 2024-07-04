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
    public List<Court> getAllCourt() {
        return courtRepository.findAll();
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
        CourtDto courtDtoList;
        // List<SlotOfCourtDto> slotOfCourtDtoList = new ArrayList<>();
        List<SubCourtDto> subCourtDtoList = new ArrayList<>();


        List<Price> price = priceRepository.getPriceByCourtID(court.getCourtID());
        // List<SlotOfCourt> slotOfCourtList = slotOfCourtService.getSlotByID(i.getCourtID());
        List<SubCourt> subCourtList = subCourtRepository.getSubCourtByCourtID(court.getCourtID());


        for (SubCourt s : subCourtList) {

            subCourtDtoList.add(new SubCourtDto(s.getSubCourtID(), s.getSubCourtName(), s.isSubCourtStatus()));
        }

        return new CourtDto(
                court.getCourtID(), court.getCourtName(), court.getDistrict(), court.getCourtAddress(), court.getCourtQuantity(), court.getDuration(),
                court.getImages(), subCourtDtoList, price, court.getOpenTime(), court.getCloseTime(), court.getUser().getUserID());


    }

    @Override
    public CreateCourtResponse createNewCourt(CreateCourtRequest createCourtRequest) {
        Court court = new Court();
        court.setCourtName(createCourtRequest.getCourtName());
        court.setCourtAddress(createCourtRequest.getCourtAddress());
        court.setDistrict(createCourtRequest.getDistrict());
        court.setDuration(createCourtRequest.getDuration());
        court.setOpenTime(createCourtRequest.getOpenTime());
        court.setCloseTime(createCourtRequest.getCloseTime());
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
        return CreateCourtResponse.builder().court(court).build();
    }
}
