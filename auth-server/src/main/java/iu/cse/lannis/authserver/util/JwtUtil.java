package iu.cse.lannis.authserver.util;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.security.Keys;
import iu.cse.lannis.authserver.dto.StudentDto;
import iu.cse.lannis.authserver.dto.UserSignInDto;
import iu.cse.lannis.authserver.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Claims getClaims(final String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateTokenSignUp(UserSignUpDto dto, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", dto.getId());
        claims.put("email", dto.getEmail());
        claims.put("username", dto.getUsername());
        return doGenerateToken(claims, dto.getEmail(), type);
    }

    public String generateTokenSignIn(StudentDto dto, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", dto.getId());
        claims.put("email", dto.getEmail());
        claims.put("username", dto.getUsername());
        return doGenerateToken(claims, dto.getEmail(), type);
    }

    private String doGenerateToken(Map<String, Object> claims, String username, String type) {
        long expirationTimeLong;
        if ("ACCESS".equals(type)) {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
