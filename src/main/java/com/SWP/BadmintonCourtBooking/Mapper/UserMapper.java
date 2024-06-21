package com.SWP.BadmintonCourtBooking.Mapper;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;

import com.SWP.BadmintonCourtBooking.Dto.Response.CreateNewUserResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.RegisterResponse;
import com.SWP.BadmintonCourtBooking.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);

    //void updateUser(@MappingTarget User user, UserUpdateRequest request);

    RegisterResponse toUserResponse(User user);
    CreateNewUserResponse toCreateNewUserResponse(User user);
}
