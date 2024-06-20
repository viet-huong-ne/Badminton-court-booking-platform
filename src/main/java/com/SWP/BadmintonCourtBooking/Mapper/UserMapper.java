package com.SWP.BadmintonCourtBooking.Mapper;

import com.SWP.BadmintonCourtBooking.Dto.Request.RegisterRequest;
import com.SWP.BadmintonCourtBooking.Dto.Respone.RegisterRespone;
import com.SWP.BadmintonCourtBooking.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);

    //void updateUser(@MappingTarget User user, UserUpdateRequest request);

    RegisterRespone toUserResponse(User user);
}
