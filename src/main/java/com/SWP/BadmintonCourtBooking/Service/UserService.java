package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UpdateUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //API: AUTHENTICATION
    //TODO: REGISTER
    RegisterResponse registerUser(RegisterRequest request);

    //TODO: LOGIN

    //API: PROFILE USER
    //TODO: UPDATE PROFILE - NÓ KHÔNG UPDATE THÔNG TIN NÀO THÌ PHẢI GIỮ NGUYÊN THÔNG TIN ĐÓ
    UpdateUserResponse updateInforUser(UpdateUserRequest request);

    //TODO: CHANGE PASSWORD


    //TODO: FORGOT PASSWORD


    //TODO: DELETE USER
    boolean deleteUser(DeleteUserRequest deleteUserRequest);

}
