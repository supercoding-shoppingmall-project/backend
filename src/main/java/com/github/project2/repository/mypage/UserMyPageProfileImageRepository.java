package com.github.project2.repository.mypage;


import com.github.project2.entity.mypage.UserMyPageProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMyPageProfileImageRepository extends JpaRepository<UserMyPageProfileImage, Integer> {
	Optional<UserMyPageProfileImage> findByUserId(Integer userId);
}
