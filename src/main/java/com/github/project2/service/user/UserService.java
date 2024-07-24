package com.github.project2.service.user;

import com.github.project2.dto.user.UserBody;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.entity.user.enums.Gender;
import com.github.project2.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


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
}
