package com.github.project2.controller.user;

import com.github.project2.dto.user.LoginRequest;
import com.github.project2.dto.user.UserBody;
import com.github.project2.repository.user.UserRepository;
import com.github.project2.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final HttpServletResponse httpServletResponse;

    @Tag(name = "post", description = "회원가입 API")
    @Operation(summary = "이메일로 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserBody userBody) {
        userService.signup(userBody);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @Tag(name = "post", description = "로그인 API")
    @Operation(summary = "이메일과 비밀번호로 로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        log.info(token);
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return ResponseEntity.ok("로그인이 성공적으로 완료되었습니다.");
    }
}
