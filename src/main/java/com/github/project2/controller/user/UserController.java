package com.github.project2.controller.user;

import com.github.project2.dto.user.UserBody;
import com.github.project2.repository.user.UserRepository;
import com.github.project2.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Tag(name = "posts", description = "회원가입 API")
    @Operation(summary = "이메일로 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserBody userBody) {
        userService.signup(userBody);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }
}
