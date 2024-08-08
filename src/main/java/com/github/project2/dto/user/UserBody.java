package com.github.project2.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBody {
    @Schema(description = "사용자 이메일")
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String gender;

}
