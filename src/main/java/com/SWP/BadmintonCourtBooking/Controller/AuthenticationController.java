package com.SWP.BadmintonCourtBooking.Controller;


import com.SWP.BadmintonCourtBooking.Dto.Request.AuthenticationRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.VerifyTokenRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.APIResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.AuthenticationResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.VerifyTokenResponse;
import com.SWP.BadmintonCourtBooking.Service.Impl.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor //có thể autowired các bean
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationServiceImpl authenticationService;

    @PostMapping("/login")
    APIResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return APIResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/verifyToken")
    APIResponse<VerifyTokenResponse> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest) throws ParseException, JOSEException {
        var result = authenticationService.verifyToken(verifyTokenRequest);
        return APIResponse.<VerifyTokenResponse>builder()
                .result(result)
                .build();
    }
}
