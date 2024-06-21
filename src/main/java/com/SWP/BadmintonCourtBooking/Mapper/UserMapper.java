package com.SWP.BadmintonCourtBooking.Mapper;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;

import com.SWP.BadmintonCourtBooking.Dto.Request.UpdateUserRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UpdateUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.UserResponse;
import com.SWP.BadmintonCourtBooking.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);

    RegisterResponse toUserResponse(User user);

    CreateNewUserResponse toCreateNewUserResponse(User user);

    UpdateUserResponse toUpdateUserResponse(User user);

    UserResponse toOneUserResponse(User user);

    List<UserResponse> toListUserResponse(List<User> list);
}
