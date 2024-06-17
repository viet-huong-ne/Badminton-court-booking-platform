package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.UserDto;
import com.SWP.BadmintonCourtBooking.Entity.Role;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.SWP.BadmintonCourtBooking.Repository.RoleRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    //@Autowired
    //private PasswordEncoder passwordEncoder;
//    @Override
//    public User saveUser(UserDto userDto) {
//        Role role = roleRepository.findByName("Customer");
//        User user = new User(userDto.getEmail(),null, null, userDto.getPassword(), userDto.getFullName(),role);
//        userRepository.save(user);
//        return user;
//    }

    @Override
    public boolean checkPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user.getPassword().equals(password)) return true;
        return false;
    }

    @Override
    public boolean checkEmailUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) { return true;}
        return false;
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
