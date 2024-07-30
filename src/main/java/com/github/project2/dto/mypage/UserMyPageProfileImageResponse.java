package com.github.project2.dto.mypage;

import lombok.Data;

@Data
public class UserMyPageProfileImageResponse {
	private Integer id;
	private Integer userId;
	private String profileImageUrl;
}
