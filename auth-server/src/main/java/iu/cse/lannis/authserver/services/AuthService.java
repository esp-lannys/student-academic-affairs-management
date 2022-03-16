package iu.cse.lannis.authserver.services;

import iu.cse.lannis.authserver.dto.StudentDto;
import iu.cse.lannis.authserver.dto.UserSignInDto;
import iu.cse.lannis.authserver.dto.UserSignUpDto;
import iu.cse.lannis.authserver.entities.AuthResponse;
import iu.cse.lannis.authserver.entities.AuthSignInRequest;
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

        var dto = restTemplate.postForObject("http://service-student/students", authSignUpRequest, UserSignUpDto.class);
        Assert.notNull(dto, "Failed to register new student");
        String accessToken = jwtUtil.generateTokenSignUp(dto, "ACCESS");
        String refreshToken = jwtUtil.generateTokenSignUp(dto, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(AuthSignInRequest authSignInRequest) {
        var dto = restTemplate.getForObject("http://service-student/students/search?username=" + authSignInRequest.getUsername(), StudentDto.class);
        Assert.notNull(dto, "Username or password is not correct");
        Assert.isTrue(BCrypt.checkpw(authSignInRequest.getPassword(), dto.getPassword()), "Username or password is not correct");
        String accessToken = jwtUtil.generateTokenSignIn(dto, "ACCESS");
        String refreshToken = jwtUtil.generateTokenSignIn(dto, "REFRESH");
        return new AuthResponse(accessToken, refreshToken);
    }
}
