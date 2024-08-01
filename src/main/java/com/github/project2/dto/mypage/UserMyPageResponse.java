package com.github.project2.dto.mypage;

import com.github.project2.entity.user.UserEntity;
import lombok.Data;

@Data
public class UserMyPageResponse {
	private String email;
	private String name;
	private String phone;
	private String address;
	private String gender;

	// 스태틱 팩토리 메서드
	public static UserMyPageResponse from(UserEntity user) {
		UserMyPageResponse response = new UserMyPageResponse();
		response.email = user.getEmail();
		response.name = user.getName();
		response.phone = user.getPhone();
		response.address = user.getAddress();
		response.gender = user.getGender().name();
		return response;
	}
}
