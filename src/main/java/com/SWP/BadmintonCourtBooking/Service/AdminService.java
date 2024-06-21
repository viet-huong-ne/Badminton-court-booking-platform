package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    //TODO: CREATE NEW USER
    public CreateNewUserResponse createNewUser(CreateNewUserRequest request);

    //TODO: UPDATE INFO USER
    //public UserUpdateResponse updateUser(String userId, UserUpdateRequest request)

    //TODO: GET INFO USER

    //TODO: GET ALL USER


}
