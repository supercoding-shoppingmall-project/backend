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

	@GetMapping("/{id}")
	public ResponseEntity<UserMyPageResponse> getUserMyPage(@PathVariable Integer id) {
		return ResponseEntity.ok(userMyPageService.getUserById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserMyPageResponse> updateUser(@PathVariable Integer id, @RequestBody UserMyPageUpdateRequest  userMyPageUpdateRequest) {
		return ResponseEntity.ok(userMyPageService.updateUser(id, userMyPageUpdateRequest));
	}

	@PostMapping("/profile-image/{userId}")
	public ResponseEntity<UserMyPageProfileImageResponse> createAndUpdateUserProfileImage(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) throws IOException {
		UserMyPageProfileImageResponse response = userMyPageService.createAndUpdateUserProfileImage(userId, file);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/profile-image/{id}")
	public ResponseEntity<UserMyPageProfileImageResponse> getUserProfileImage(@PathVariable Integer id) {
		return ResponseEntity.ok(userMyPageService.getUserProfileImageById(id));
	}

	@PutMapping("/profile-image/{userId}")
	public ResponseEntity<UserMyPageProfileImageResponse> updateUserProfileImage(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) throws IOException {
		UserMyPageProfileImageResponse response = userMyPageService.updateUserProfileImage(userId, file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
