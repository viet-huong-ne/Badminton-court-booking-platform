package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Respone.APIRespone;
import com.SWP.BadmintonCourtBooking.Dto.Respone.RegisterRespone;
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
    public APIRespone<RegisterRespone> registerUser(@RequestBody @Valid RegisterRequest request){
        APIRespone<RegisterRespone> apiRespone = new APIRespone<>();
        apiRespone.setResult(userService.registerUser(request));
        return apiRespone;
    }
}
