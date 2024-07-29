package com.github.project2.dto.mypage;

import lombok.Data;

@Data
public class UserMyPageUpdateRequest {
	private String email;
	private String name;
	private String phone;
	private String address;
	private String gender;
}
