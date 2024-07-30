package com.github.project2.filter;

import com.github.project2.config.security.JwtTokenProvider;
import com.github.project2.entity.user.CustomUserDetails;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.info("Request URI: " + requestURI);

        // 회원가입, 로그인, 물품 조회 요청 필터 통과
        if ("/api/user/login".equals(requestURI)
                || "/api/user/signup".equals(requestURI)
                || "/api/product/all".equals(requestURI)
                || "/api/cart/*".equals(requestURI)
                || "/api/checkout".equals(requestURI)
                || "/api/sell".equals(requestURI)
                || requestURI.startsWith("/api/cart")
                || requestURI.startsWith("/api/product/category")
                || requestURI.startsWith("/api/product")
                || requestURI.startsWith("/api/product/header")){
                || requestURI.startsWith("/api/sell")
                || requestURI.startsWith("/api/product/category")) {
            filterChain.doFilter(request, response);
            return;
        }

        // token 검사
        String jwtToken = jwtTokenProvider.resolveToken(request);
        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = jwtTokenProvider.extractUserEmail(jwtToken);
            log.info("Extracted Email: " + email);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            log.info("User Details: " + userDetails );

            if (userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUserDetail = (CustomUserDetails) userDetails;
                log.info("User ID: " + customUserDetail.getId());
                log.info("User Email: " + customUserDetail.getEmail());
                log.info("User Password: " + customUserDetail.getPassword());
            }
                if (jwtTokenProvider.validateToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("인증 성공");

                    // 로그아웃 요청 처리
                    if ("/api/user/logout".equals(requestURI) && "POST".equals(request.getMethod())) {
                        handleLogout(request, response, jwtToken);
                        return;
                    }
                } else {
                    log.info("Invalid token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 token입니다.");
                    return;
                }
            }
        filterChain.doFilter(request, response);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response, String jwtToken) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (userService.isTokenBlacklisted(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("이미 존재하는 blacklist token입니다.");
        } else {
            userService.blacklistToken(jwtToken);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("로그아웃이 정상적으로 완료됐습니다.");
        }
    }
}