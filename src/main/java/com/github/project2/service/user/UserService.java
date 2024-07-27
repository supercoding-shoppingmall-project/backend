package com.github.project2.service.user;

import com.github.project2.config.security.JwtTokenProvider;
import com.github.project2.dto.user.LoginRequest;
import com.github.project2.dto.user.UserBody;
import com.github.project2.entity.user.BlackListTokenEntity;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.entity.user.enums.Gender;
import com.github.project2.repository.user.BlacklistTokenRepository;
import com.github.project2.repository.user.UserRepository;
import com.github.project2.service.exceptions.InvalidValueException;
import com.github.project2.service.exceptions.NotAcceptException;
import com.github.project2.service.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistTokenRepository blacklistTokenRepository;

    public void signup(UserBody userBody) {
        UserEntity foundedUser = userRepository.findUserByEmail(userBody.getEmail());
        if (foundedUser != null) {
            throw new InvalidValueException("이미 존재하는 Email입니다.");
        }
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
        if (!foundedUser.getPassword().equals(loginRequest.getPassword()) ) { // 비밀번호 비교
            throw new InvalidValueException("비밀번호가 다릅니다.");
        }
        // token 생성
        String token = jwtTokenProvider.generateToken(loginRequest.getEmail());
        return token;
    }


    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }
        // UserDetails 객체를 생성하여 반환
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }

    public boolean isTokenBlacklisted(String token) {
        Optional<BlackListTokenEntity> blackListTokenEntity = blacklistTokenRepository.findByToken(token);
        return blackListTokenEntity.isPresent();
    }
    
    public void blacklistToken(String jwtToken) {
        if (isTokenBlacklisted(jwtToken)) {
            return;
        }
        BlackListTokenEntity blackListTokenEntity = new BlackListTokenEntity();
        blackListTokenEntity.setToken(jwtToken);
        blacklistTokenRepository.save(blackListTokenEntity);
    }
}
