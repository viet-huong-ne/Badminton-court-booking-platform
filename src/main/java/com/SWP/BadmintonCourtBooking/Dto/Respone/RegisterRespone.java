package com.SWP.BadmintonCourtBooking.Dto.Respone;

import com.SWP.BadmintonCourtBooking.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRespone {
    private String id;
    private String userName;
    //private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
}
