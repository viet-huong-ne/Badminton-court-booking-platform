package com.SWP.BadmintonCourtBooking.Dto.Request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNewUserRequest {
    @Size(min = 6, message = "USERNAME_INVALID")
    private String userName;

    @Size(min = 8, message = "INVALID_PASSWORD") //Phải xài key của errorMessage
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Integer roleId;
}
