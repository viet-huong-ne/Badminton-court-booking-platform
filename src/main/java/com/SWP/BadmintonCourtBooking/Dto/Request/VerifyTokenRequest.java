package com.SWP.BadmintonCourtBooking.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyTokenRequest {
    String token;
}
