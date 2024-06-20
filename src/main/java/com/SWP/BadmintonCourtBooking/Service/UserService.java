package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Respone.RegisterRespone;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //TODO: REGISTER
    RegisterRespone registerUser(RegisterRequest request);

}
