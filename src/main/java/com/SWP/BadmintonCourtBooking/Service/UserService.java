package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //API: AUTHENTICATION
    //TODO: REGISTER
    RegisterResponse registerUser(RegisterRequest request);

    //TODO: LOGIN

    //API: PROFILE USER
    //TODO: UPDATE PROFILE

    //TODO: CHANGE PASSWORD

    //TODO: FORGOT PASSWORD

}
