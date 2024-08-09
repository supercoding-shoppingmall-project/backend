package com.github.project2.repository.mypage;

import com.github.project2.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMyPageRepository extends JpaRepository<UserEntity, Integer> {
}
