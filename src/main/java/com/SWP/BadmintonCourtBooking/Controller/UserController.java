package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.UserDto;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.SWP.BadmintonCourtBooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> save(@RequestBody UserDto userDto) {
        if (userService.checkEmailUser(userDto.getEmail())) {
            return ResponseEntity.ok("This email address is already in use");
        }
        userService.saveUser(userDto);
        return ResponseEntity.ok("Add successfully");
    }


}
