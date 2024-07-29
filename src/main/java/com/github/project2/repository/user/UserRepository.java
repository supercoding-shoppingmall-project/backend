package com.github.project2.repository.user;

import com.github.project2.entity.user.BlackListTokenEntity;
import com.github.project2.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
