package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.Request.AuthenticationRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.VerifyTokenRequest;
import com.SWP.BadmintonCourtBooking.Dto.Response.AuthenticationResponse;
import com.SWP.BadmintonCourtBooking.Dto.Response.VerifyTokenResponse;
import com.SWP.BadmintonCourtBooking.Exception.AppException;
import com.SWP.BadmintonCourtBooking.Exception.ErrorCode;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;

    @NonFinal
    //protected String SECRET_KEY = "47ZuFl0dJhb54+lEvuriWkJ4XyoBwUB15YyQRsoqbily5ZMEwgcz7ajKTGCKajOh";
    @Value("${SECRET_KEY}")
    protected String SECRET_KEY;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //Get user lên
        var user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUserName());
        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    @Override
    public String generateToken(String username) {
        //Để tạo token bằng thư viện
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //Các data trong body thì gọi là claims
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) //đại diện cho user đăng nhập,
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(5, ChronoUnit.MINUTES).toEpochMilli())) //hết hạn sau 5 minutes
                .claim("CustomClaim", "Customer")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload); //cần header và payload
        //Ký token
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    @Override
    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        System.out.println(token);
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        //Kiểm tra token hết hạn hay chưa
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier); //trả về boolean

        return VerifyTokenResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }
}
