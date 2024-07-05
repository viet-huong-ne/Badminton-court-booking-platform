package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UpdateUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    //TODO: CREATE NEW USER
    public CreateNewUserResponse createNewUser(CreateNewUserRequest request);

    //TODO: UPDATE INFO USER
    public UpdateUserResponse updateUser(Integer userId, UpdateUserRequest request);

    //TODO: GET INFO USER
    public UserResponse getUserById(Integer id);

    //TODO: GET LIST USER
    public List<UserResponse> getListUser();

    //TODO: DELETE USER


}
