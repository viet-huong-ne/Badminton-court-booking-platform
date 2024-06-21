package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.UserDto;
import com.SWP.BadmintonCourtBooking.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //User saveUser(UserDto userDto);
    boolean checkPassword(String email, String password);
    boolean checkEmailUser(String email);
    User getUser(String email);
}
