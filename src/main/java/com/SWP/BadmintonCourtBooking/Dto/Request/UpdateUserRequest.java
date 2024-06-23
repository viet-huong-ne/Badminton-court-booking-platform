package com.SWP.BadmintonCourtBooking.Dto.Request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    //private String password;
    private String phone;

    //private String roleName;
    private Integer roleID;

}
