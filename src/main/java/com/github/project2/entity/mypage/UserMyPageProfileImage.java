package com.github.project2.entity.mypage;


import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "UserProfileImage")
public class UserMyPageProfileImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(name = "profile_image_url", nullable = false)
	private String profileImageUrl;

	// 스태틱 팩토리 메서드
	public static UserMyPageProfileImage from(UserEntity user, String profileImageUrl) {
		UserMyPageProfileImage profileImage = new UserMyPageProfileImage();
		profileImage.user = user;
		profileImage.profileImageUrl = profileImageUrl;
		return profileImage;
	}
}
