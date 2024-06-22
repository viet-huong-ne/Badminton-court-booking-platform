package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    //private Integer userId;
    private Integer userID;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    //private String password;
    private String phone;
    //private Integer roleID;
    private Role role;
}
