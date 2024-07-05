package com.SWP.BadmintonCourtBooking.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    UserResponse response;
    boolean isAuthenticated;
}
