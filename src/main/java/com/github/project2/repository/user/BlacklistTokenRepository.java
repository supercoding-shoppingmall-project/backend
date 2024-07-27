package com.github.project2.repository.user;

import com.github.project2.entity.user.BlackListTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistTokenRepository extends JpaRepository<BlackListTokenEntity, Integer> {
    Optional<BlackListTokenEntity> findByToken(String token);
}
