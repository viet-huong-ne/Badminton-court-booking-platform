package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.SWP.BadmintonCourtBooking.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNewUserResponse {
    private Integer id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private Role role;
}
