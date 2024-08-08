package com.github.project2.controller.mypage;

import com.github.project2.dto.mypage.*;
import com.github.project2.service.mypage.UserMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class UserMyPageController {

	private final UserMyPageService userMyPageService;

	/**
	 * 사용자의 마이페이지 정보를 조회하는 엔드포인트.
	 *
	 * @param id 사용자 ID
	 * @return 사용자 마이페이지 정보
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserMyPageResponse> getUserMyPage(@PathVariable Integer id) {
		return ResponseEntity.ok(userMyPageService.getUserById(id));
	}

	/**
	 * 사용자의 정보를 업데이트하는 엔드포인트.
	 *
	 * @param id 사용자 ID
	 * @param userMyPageUpdateRequest 사용자 업데이트 요청 데이터
	 * @return 업데이트된 사용자 정보
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UserMyPageResponse> updateUser(@PathVariable Integer id, @RequestBody UserMyPageUpdateRequest userMyPageUpdateRequest) {
		return ResponseEntity.ok(userMyPageService.updateUser(id, userMyPageUpdateRequest));
	}

	/**
	 * 사용자의 프로필 이미지를 생성 또는 업데이트하는 엔드포인트.
	 *
	 * @param userId 사용자 ID
	 * @param file 업로드할 프로필 이미지 파일
	 * @return 생성 또는 업데이트된 프로필 이미지 정보
	 * @throws IOException 파일 업로드 중 발생할 수 있는 예외
	 */
	@PostMapping("/profile-image/{userId}")
	public ResponseEntity<UserMyPageProfileImageResponse> createAndUpdateUserProfileImage(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) throws IOException {
		UserMyPageProfileImageResponse response = userMyPageService.createAndUpdateUserProfileImage(userId, file);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * 사용자의 프로필 이미지를 조회하는 엔드포인트.
	 *
	 * @param id 사용자 ID
	 * @return 사용자 프로필 이미지 정보
	 */
	@GetMapping("/profile-image/{id}")
	public ResponseEntity<UserMyPageProfileImageResponse> getUserProfileImage(@PathVariable Integer id) {
		return ResponseEntity.ok(userMyPageService.getUserProfileImageById(id));
	}

	/**
	 * 사용자의 프로필 이미지를 업데이트하는 엔드포인트.
	 *
	 * @param userId 사용자 ID
	 * @param file 업로드할 새로운 프로필 이미지 파일
	 * @return 업데이트된 프로필 이미지 정보
	 * @throws IOException 파일 업로드 중 발생할 수 있는 예외
	 */
	@PutMapping("/profile-image/{userId}")
	public ResponseEntity<UserMyPageProfileImageResponse> updateUserProfileImage(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) throws IOException {
		UserMyPageProfileImageResponse response = userMyPageService.updateUserProfileImage(userId, file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}