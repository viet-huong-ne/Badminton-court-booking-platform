package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //TODO: REGISTER
    RegisterResponse registerUser(RegisterRequest request);

}
