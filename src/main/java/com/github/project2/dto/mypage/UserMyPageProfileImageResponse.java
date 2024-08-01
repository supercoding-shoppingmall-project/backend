package com.github.project2.dto.mypage;

import com.github.project2.entity.mypage.UserMyPageProfileImage;
import lombok.Data;

@Data
public class UserMyPageProfileImageResponse {
	private Integer id;
	private Integer userId;
	private String profileImageUrl;

	// 스태틱 팩토리 메서드
	public static UserMyPageProfileImageResponse from(UserMyPageProfileImage profileImage) {
		UserMyPageProfileImageResponse response = new UserMyPageProfileImageResponse();
		response.id = profileImage.getId();
		response.userId = profileImage.getUser().getId();
		response.profileImageUrl = profileImage.getProfileImageUrl();
		return response;
	}
}
