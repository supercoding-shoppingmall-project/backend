//package com.github.project2.service.security;
//
//import com.github.project2.entity.user.UserEntity;
//import com.github.project2.repository.user.CustomUserDetails;
//import com.github.project2.repository.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.stream.Collectors;
//
//@Primary
//@RequiredArgsConstructor
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findUserByEmail(email);
//
//        CustomUserDetails customUserDetails = CustomUserDetails.builder()
//                .userId(userEntity.getId())
//                .email(userEntity.getEmail())
//                .password(userEntity.getPassword())
//                .authorities(userEntity.getRole())
//                .build();
//
//        return customUserDetails;
//    }
//}
