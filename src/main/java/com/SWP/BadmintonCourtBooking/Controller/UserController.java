package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;

import com.SWP.BadmintonCourtBooking.Dto.Response.APIResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
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

    @PostMapping("/register")
    public APIResponse<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest request){
        //APIResponse<RegisterResponse> apiResponse = new APIResponse<>();
        var result = userService.registerUser(request);
        return APIResponse.<RegisterResponse>builder()
                .result(result)
                .build();

    }
}
