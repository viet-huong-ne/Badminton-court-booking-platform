package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.APIResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
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
}
