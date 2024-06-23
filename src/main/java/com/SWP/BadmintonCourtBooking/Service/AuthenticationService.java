package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.Request.AuthenticationRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.VerifyTokenRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.AuthenticationResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.VerifyTokenResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public String generateToken(String username);
    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws JOSEException, ParseException;
}
