package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.APIResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;


import com.SWP.BadmintonCourtBooking.Dto.Response.UpdateUserResponse;
import com.SWP.BadmintonCourtBooking.Service.AdminService;
import com.SWP.BadmintonCourtBooking.Service.Impl.AdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @PostMapping("/createUser")
    public APIResponse<CreateNewUserResponse> createUser(@RequestBody @Valid CreateNewUserRequest request){
        var result = adminServiceImpl.createNewUser(request);
        return APIResponse.<CreateNewUserResponse>builder()
                .result(result)
                .build();
    }


    @PutMapping("/updateUser/{userId}")
    public APIResponse<UpdateUserResponse> createUser(@PathVariable Integer userId, @RequestBody @Valid UpdateUserRequest request){
        var result = adminServiceImpl.updateUser(userId, request);
        return APIResponse.<UpdateUserResponse>builder()
                .result(result)
                .build();
    }
}

