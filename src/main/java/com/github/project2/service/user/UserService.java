package com.github.project2.service.user;

import com.github.project2.config.security.JwtTokenProvider;
import com.github.project2.dto.user.LoginRequest;
import com.github.project2.dto.user.UserBody;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.entity.user.enums.Gender;
import com.github.project2.repository.user.UserRepository;
import com.github.project2.service.exceptions.InvalidValueException;
import com.github.project2.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public void signup(UserBody userBody) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userBody.getEmail());
        userEntity.setPassword(userBody.getPassword());
        userEntity.setName(userBody.getName());
        userEntity.setPhone(userBody.getPhone());
        userEntity.setAddress(userBody.getAddress());
        userEntity.setGender(Gender.valueOf(userBody.getGender().toUpperCase()));
        userRepository.save(userEntity);
    }

    public String login(LoginRequest loginRequest) {
        UserEntity foundedUser = userRepository.findUserByEmail(loginRequest.getEmail());
        if (foundedUser == null) {
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }
        if (!foundedUser.getPassword().equals(loginRequest.getPassword()) ) { // 암호화된 비밀번호 비교
            throw new InvalidValueException("비밀번호가 다릅니다.");
        }
        // token 생성
        String token = jwtTokenProvider.generateToken(loginRequest.getEmail());
        return token;
    }
}
