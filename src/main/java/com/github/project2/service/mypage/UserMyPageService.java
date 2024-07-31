package com.github.project2.service.mypage;


import com.github.project2.dto.mypage.*;
import com.github.project2.entity.mypage.UserMyPageProfileImage;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.entity.user.enums.Gender;
import com.github.project2.repository.mypage.UserMyPageProfileImageRepository;
import com.github.project2.repository.mypage.UserMyPageRepository;
import com.github.project2.service.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMyPageService {

	private final UserMyPageRepository userMyPageRepository;
	private final UserMyPageProfileImageRepository userMyPageProfileImageRepository;
	private final S3Service s3Service;

	// 사용자 정보를 ID로 조회하여 반환하는 메서드
	public UserMyPageResponse getUserById(Integer id) {
		UserEntity user = userMyPageRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return toUserMyPageResponse(user);
	}

	public UserMyPageResponse updateUser(Integer id, UserMyPageUpdateRequest userMyPageUpdateRequest) {
		UserEntity user = userMyPageRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found"));

		user.setEmail(userMyPageUpdateRequest.getEmail());
		user.setName(userMyPageUpdateRequest.getName());
		user.setPhone(userMyPageUpdateRequest.getPhone());
		user.setAddress(userMyPageUpdateRequest.getAddress());
		user.setGender(Gender.valueOf(userMyPageUpdateRequest.getGender()));

		UserEntity updatedUser = userMyPageRepository.save(user);
		return toUserMyPageResponse(updatedUser);
	}

	private UserMyPageResponse toUserMyPageResponse(UserEntity user) {
		UserMyPageResponse response = new UserMyPageResponse();
		response.setEmail(user.getEmail());
		response.setName(user.getName());
		response.setPhone(user.getPhone());
		response.setAddress(user.getAddress());
		response.setGender(user.getGender().name());
		return response;
	}

	public UserMyPageProfileImageResponse createAndUpdateUserProfileImage(Integer userId, MultipartFile file) throws IOException {
		UserEntity user = userMyPageRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		// 프로필 이미지 업로드
		String profileImageUrl = s3Service.uploadFile(file);  // S3Service를 사용하여 파일 업로드

		// 프로필 이미지가 이미 존재하는지 확인
		Optional<UserMyPageProfileImage> existingProfileImage = userMyPageProfileImageRepository.findByUserId(userId);

		if (existingProfileImage.isPresent()) {
			// 존재하면 업데이트 수행
			UserMyPageProfileImage userMyPageProfileImage = existingProfileImage.get();
			userMyPageProfileImage.setProfileImageUrl(profileImageUrl);
			userMyPageProfileImageRepository.save(userMyPageProfileImage);
			return toUserMyPageProfileImageResponse(userMyPageProfileImage);
		} else {
			// 존재하지 않으면 새로 생성
			UserMyPageProfileImage userMyPageProfileImage = new UserMyPageProfileImage();
			userMyPageProfileImage.setUser(user);
			userMyPageProfileImage.setProfileImageUrl(profileImageUrl);

			UserMyPageProfileImage savedProfileImage = userMyPageProfileImageRepository.save(userMyPageProfileImage);
			return toUserMyPageProfileImageResponse(savedProfileImage);
		}
	}

	public UserMyPageProfileImageResponse getUserProfileImageById(Integer id) {
		UserMyPageProfileImage userMyPageProfileImage = userMyPageProfileImageRepository.findByUserId(id)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return toUserMyPageProfileImageResponse(userMyPageProfileImage);
	}


	public UserMyPageProfileImageResponse updateUserProfileImage(Integer id, MultipartFile file) throws IOException {
		UserMyPageProfileImage userMyPageProfileImage = userMyPageProfileImageRepository.findByUserId(id)
				.orElseThrow(() -> new NotFoundException("User not found"));


		String profileImageUrl = s3Service.uploadFile(file);

		userMyPageProfileImage.setProfileImageUrl(profileImageUrl);
		userMyPageProfileImageRepository.save(userMyPageProfileImage);

		return toUserMyPageProfileImageResponse(userMyPageProfileImage);
	}

	private UserMyPageProfileImageResponse toUserMyPageProfileImageResponse(UserMyPageProfileImage userMyPageProfileImage) {
		UserMyPageProfileImageResponse response = new UserMyPageProfileImageResponse();
		response.setId(userMyPageProfileImage.getId());
		response.setUserId(userMyPageProfileImage.getUser().getId());
		response.setProfileImageUrl(userMyPageProfileImage.getProfileImageUrl());
		return response;
	}

}
