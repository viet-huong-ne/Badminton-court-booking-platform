package com.SWP.BadmintonCourtBooking.Dto.Request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Size(min = 6, message = "USERNAME_INVALID")
    private String userName;

    @Size (min = 8, message = "INVALID_PASSWORD") //Phải xài key của errorMessage
    private String password;

    //private String firstName;

    //private String lastName;

    private String email;

    private String phone;

    //Default
    //private Role role = USER;
}
