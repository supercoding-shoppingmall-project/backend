package com.github.project2.entity.user;


import com.github.project2.dto.mypage.UserMyPageUpdateRequest;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.user.enums.Gender;
import com.github.project2.entity.user.enums.Role;
import com.github.project2.entity.user.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name ="phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "sellerId",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ProductEntity> productEntityList;

    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = Role.USER;
        }
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // 사용자 정보를 업데이트하는 메서드
    public void updateDetails(UserMyPageUpdateRequest request) {
        this.email = request.getEmail();
        this.name = request.getName();
        this.phone = request.getPhone();
        this.address = request.getAddress();
        this.gender = Gender.valueOf(request.getGender());
    }

    // 스태틱 팩토리 메서드
    public static UserEntity from(UserMyPageUpdateRequest request) {
        UserEntity user = new UserEntity();
        user.updateDetails(request);
        return user;
    }
}