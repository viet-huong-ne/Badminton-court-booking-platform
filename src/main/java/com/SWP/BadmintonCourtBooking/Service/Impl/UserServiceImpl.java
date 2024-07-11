package com.SWP.BadmintonCourtBooking.Service.Impl;


import com.SWP.BadmintonCourtBooking.Dto.Request.DeleteUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterStaffRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UpdateUserResponse;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Role;
import com.SWP.BadmintonCourtBooking.Entity.Staff;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
import com.SWP.BadmintonCourtBooking.Mapper.UserMapper;
import com.SWP.BadmintonCourtBooking.Repository.CourtRepository;
import com.SWP.BadmintonCourtBooking.Repository.RoleRepository;
import com.SWP.BadmintonCourtBooking.Repository.StaffRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private CourtRepository courtRepository;

    //API AUTHENTICATION
    //TODO: REGISTER
    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        //User user = userMapper.toUser(request); //tương đương với đoạn code dưới
        User user = new User();
        user.setUserName(request.getUserName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        Role role = roleRepository.findByName("Customer");
        user.setRole(role);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UpdateUserResponse updateInforUser(UpdateUserRequest request) {
        //tìm user đó
        if (!userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = new User();


        //update thông tin user

        return null;
    }


    //TODO: LOGIN


    //API: PROFILE USER
    //TODO: UPDATE PROFILE


    //TODO: CHANGE PASSWORD


    //TODO: FORGOT PASSWORD

    //TODO: DELETE USER
    @Override
    public boolean deleteUser(DeleteUserRequest deleteUserRequest) {
        //tìm ra user
        User user = userRepository.findById(deleteUserRequest.getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return true;
    }

    @Override
    public RegisterResponse regiterStaff(RegisterStaffRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = new User();
        user.setUserName(request.getUserName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        Role role = roleRepository.findByName("Staff");
        user.setRole(role);
        User user1 = userRepository.save(user);
        Court court = courtRepository.findById(request.getCourtID()).orElseThrow(() -> new RuntimeException("Court not found"));
        Staff staff = new Staff();
        staff.setUser(user1);
        staff.setCourt(court);
        staffRepository.save(staff);
        return userMapper.toUserResponse(user1);
    }

}
