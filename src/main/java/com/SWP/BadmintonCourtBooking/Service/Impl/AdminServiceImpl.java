package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.Request.CreateNewUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UpdateUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UserResponse;
import com.SWP.BadmintonCourtBooking.Entity.Role;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
import com.SWP.BadmintonCourtBooking.Mapper.UserMapper;
import com.SWP.BadmintonCourtBooking.Repository.RoleRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.AdminService;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public CreateNewUserResponse createNewUser(CreateNewUserRequest request) {
//        if(userRepository.existsByUserName(request.getUserName())){
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }
        if(userRepository.existsByUserNameOrEmail(request.getUserName(), request.getEmail()) != null){
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
        //Role role = roleRepository.findByName("User");
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        return userMapper.toCreateNewUserResponse(userRepository.save(user));
    }

    @Override
    public UpdateUserResponse updateUser(Integer userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        //Role role = roleRepository.findByName(request.getRoleName());
        //log.info(request.getRoleName());
        Role role = roleRepository.findById(request.getRoleID()).orElseThrow(()->new RuntimeException("UserID Not Found"));
        user.setRole(role);
        return userMapper.toUpdateUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(Integer id) {
        return userMapper.toOneUserResponse(userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found")));
    }

    @Override
    public List<UserResponse> getListUser() {
        return userMapper.toListUserResponse(userRepository.findAll());
    }

}
