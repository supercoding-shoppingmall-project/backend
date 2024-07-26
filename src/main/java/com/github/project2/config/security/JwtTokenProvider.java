package com.github.project2.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key-source}")
    private String secretKeySource;

    private String secretKey;

    @PostConstruct
    public void setUp(){
        secretKey = Base64.getEncoder()
                .encodeToString((secretKeySource).getBytes());
    }

    private long tokenValidMillisecond = 1000L * 60 * 60;; // 1시간

    private final UserDetailsService userDetailsService;

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Baerer");
    }

    // token 발급
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // token 유효성 검증
    public Boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody();
            Date now = new Date();
            return claims.getExpiration().before(now); // 만료시간이 현재 시간 이전인지 여부 확인
        } catch (Exception e){
            return false;
        }
    }

    // 유저 인증
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody();
//        final String extractedEmail = claims.getSubject();  // Claim 에서 Email 추출
//        UserDetails userDetails = userDetailsService.loadUserByUsername(extractedEmail);
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }


}
