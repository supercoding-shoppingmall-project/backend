package com.github.project2.entity.mypage;


import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
}
