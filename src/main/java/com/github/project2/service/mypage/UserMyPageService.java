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

	/**
	 * 사용자 정보를 ID로 조회하여 반환하는 메서드.
	 *
	 * @param id 사용자 ID.
	 * @return UserMyPageResponse 사용자 정보 응답 객체.
	 * @throws NotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없는 경우 발생.
	 */
	public UserMyPageResponse getUserById(Integer id) {
		UserEntity user = userMyPageRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return UserMyPageResponse.from(user);
	}

	/**
	 * 사용자 정보를 업데이트하는 메서드.
	 *
	 * @param id 사용자 ID.
	 * @param userMyPageUpdateRequest 사용자 정보 업데이트 요청 객체.
	 * @return UserMyPageResponse 업데이트된 사용자 정보 응답 객체.
	 * @throws NotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없는 경우 발생.
	 */
	public UserMyPageResponse updateUser(Integer id, UserMyPageUpdateRequest userMyPageUpdateRequest) {
		UserEntity user = userMyPageRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found"));

		// 사용자 정보를 업데이트
		user.updateDetails(userMyPageUpdateRequest);
		userMyPageRepository.save(user);
		return UserMyPageResponse.from(user);
	}

	/**
	 * 사용자 프로필 이미지를 생성 및 업데이트하는 메서드.
	 *
	 * @param userId 사용자 ID.
	 * @param file 업로드할 프로필 이미지 파일.
	 * @return UserMyPageProfileImageResponse 생성 또는 업데이트된 프로필 이미지 응답 객체.
	 * @throws IOException 파일 업로드 중 발생할 수 있는 예외.
	 * @throws NotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없는 경우 발생.
	 */
	public UserMyPageProfileImageResponse createAndUpdateUserProfileImage(Integer userId, MultipartFile file) throws IOException {
		UserEntity user = userMyPageRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		// 프로필 이미지 업로드
		String profileImageUrl = s3Service.uploadFile(file);

		// 프로필 이미지가 이미 존재하는지 확인
		UserMyPageProfileImage userMyPageProfileImage = userMyPageProfileImageRepository.findByUserId(userId)
				.orElseGet(() -> UserMyPageProfileImage.from(user, profileImageUrl));

		// 프로필 이미지 URL 업데이트
		userMyPageProfileImage.setProfileImageUrl(profileImageUrl);
		userMyPageProfileImageRepository.save(userMyPageProfileImage);
		return UserMyPageProfileImageResponse.from(userMyPageProfileImage);
	}

	/**
	 * 사용자 프로필 이미지를 ID로 조회하여 반환하는 메서드.
	 *
	 * @param id 사용자 ID.
	 * @return UserMyPageProfileImageResponse 프로필 이미지 응답 객체.
	 * @throws NotFoundException 사용자 ID에 해당하는 프로필 이미지를 찾을 수 없는 경우 발생.
	 */
	public UserMyPageProfileImageResponse getUserProfileImageById(Integer id) {
		UserMyPageProfileImage userMyPageProfileImage = userMyPageProfileImageRepository.findByUserId(id)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return UserMyPageProfileImageResponse.from(userMyPageProfileImage);
	}

	/**
	 * 사용자 프로필 이미지를 업데이트하는 메서드.
	 *
	 * @param id 사용자 ID.
	 * @param file 업로드할 프로필 이미지 파일.
	 * @return UserMyPageProfileImageResponse 업데이트된 프로필 이미지 응답 객체.
	 * @throws IOException 파일 업로드 중 발생할 수 있는 예외.
	 * @throws NotFoundException 사용자 ID에 해당하는 프로필 이미지를 찾을 수 없는 경우 발생.
	 */
	public UserMyPageProfileImageResponse updateUserProfileImage(Integer id, MultipartFile file) throws IOException {
		UserMyPageProfileImage userMyPageProfileImage = userMyPageProfileImageRepository.findByUserId(id)
				.orElseThrow(() -> new NotFoundException("User not found"));

		// 프로필 이미지 업로드
		String profileImageUrl = s3Service.uploadFile(file);

		// 프로필 이미지 URL 업데이트
		userMyPageProfileImage.setProfileImageUrl(profileImageUrl);
		userMyPageProfileImageRepository.save(userMyPageProfileImage);
		return UserMyPageProfileImageResponse.from(userMyPageProfileImage);
	}
}