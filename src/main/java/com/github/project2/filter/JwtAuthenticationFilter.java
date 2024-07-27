package com.github.project2.filter;

import com.github.project2.config.security.JwtTokenProvider;
import com.github.project2.repository.user.BlacklistTokenRepository;
import com.github.project2.service.exceptions.InvalidValueException;
import com.github.project2.service.exceptions.NotAcceptException;
import com.github.project2.service.user.UserService;
import jakarta.persistence.Column;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.NotActiveException;
import java.net.Authenticator;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 회원가입, 로그인 요청 filter 통과
        String requestURI = request.getRequestURI();
        if ("/api/user/login".equals(requestURI) || "/api/user/signup".equals(requestURI) || "/api/product/all".equals(requestURI)) {   // 추가
            filterChain.doFilter(request, response);
            return;
        }
        // 로그아웃 요청 처리
        if ("/api/user/logout".equals(requestURI) && "POST".equals(request.getMethod())) {
            String jwtToken = jwtTokenProvider.resolveToken(request);

//            if (userService.isTokenBlacklisted(jwtToken)) {
//                // 블랙리스트에 저장된 token 이면 예외처리
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "blacklist 토큰이 이미 존재합니다.");
//            }
            if (jwtToken != null) {
                userService.blacklistToken(jwtToken);
//                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().write("로그아웃이 정상적으로 완료됐습니다.");
                return;
            }
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("로그아웃에는 token 이 필요합니다.");
            return;
        }

        String jwtToken = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.extractUserEmail(jwtToken);

        if (email != null && jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(email);

            if (jwtTokenProvider.validateToken(jwtToken)){
                // 검증 후 인증 객체 생성하여 securityContextHolder 에서 관리
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 token입니다.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}