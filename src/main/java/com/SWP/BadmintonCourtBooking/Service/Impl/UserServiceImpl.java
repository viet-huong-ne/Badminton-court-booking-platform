package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.UserDto;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.SWP.BadmintonCourtBooking.Service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void saveUser(UserDto userDto) {
        //User user = new User()
    }

    @Override
    public boolean checkPassword(String email, String password) {
        return false;
    }

    @Override
    public boolean checkEmailUser(String email) {
        return false;
    }

    @Override
    public User getUser(String email) {
        return null;
    }
}
