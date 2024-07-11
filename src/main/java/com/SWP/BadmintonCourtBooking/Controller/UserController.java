package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterStaffRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.APIResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import com.SWP.BadmintonCourtBooking.Service.Impl.UserServiceImpl;
import com.SWP.BadmintonCourtBooking.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public APIResponse<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest request){
        //APIResponse<RegisterResponse> apiResponse = new APIResponse<>();
        var result = userServiceImpl.registerUser(request);
        return APIResponse.<RegisterResponse>builder()
                .result(result)
                .build();

    }


    //API: USER


    @DeleteMapping("/deleteuser")
    public boolean deleteUser(@RequestBody @Valid DeleteUserRequest request){
        return userServiceImpl.deleteUser(request);
    }

    //TODO API TAO TAI KHOAN CHO STAFF
    @PostMapping("/registerStaff")
    public APIResponse<RegisterResponse> registerStaff(@RequestBody RegisterStaffRequest request){
        var result = userServiceImpl.regiterStaff(request);
        return APIResponse.<RegisterResponse>builder()
                .result(result)
                .build();
    }

}
