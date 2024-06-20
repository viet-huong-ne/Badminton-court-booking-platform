package com.SWP.BadmintonCourtBooking.Service.Impl;


import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Respone.RegisterRespone;
import com.SWP.BadmintonCourtBooking.Entity.Role;
import com.SWP.BadmintonCourtBooking.Entity.User;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
import com.SWP.BadmintonCourtBooking.Mapper.UserMapper;
import com.SWP.BadmintonCourtBooking.Repository.RoleRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    //TODO: REGISTER
    @Override
    public RegisterRespone registerUser(RegisterRequest request){
        if(userRepository.existsByUserName(request.getUserName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        //User user = userMapper.toUser(request); //tương đương với đoạn code dưới
        User user = new User();
        user.setUserName(request.getUserName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        Role role = roleRepository.findByName("User");
        user.setRole(role);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
