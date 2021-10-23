package iu.cse.lannis.authserver.services;

import iu.cse.lannis.authserver.dto.UserSignUpDto;
import iu.cse.lannis.authserver.entities.AuthResponse;
import iu.cse.lannis.authserver.entities.AuthSignUpRequest;
import iu.cse.lannis.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService (RestTemplate restTemplate, final JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthSignUpRequest authSignUpRequest) {

        authSignUpRequest.setPassword(BCrypt.hashpw(authSignUpRequest.getPassword(), BCrypt.gensalt()));

        UserSignUpDto dto = restTemplate.postForObject("http://service-student/students", authSignUpRequest, UserSignUpDto.class);
        Assert.notNull(dto, "Failed to register new student");
        String accessToken = jwtUtil.generateTokenSignUp(dto, "ACCESS");
        String refreshToken = jwtUtil.generateTokenSignUp(dto, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }
}
