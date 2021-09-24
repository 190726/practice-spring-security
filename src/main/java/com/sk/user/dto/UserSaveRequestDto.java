package com.sk.user.dto;

import com.sk.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSaveRequestDto {

    @NotBlank(message = "사용자 ID를 꼭 입력해주세요.")
    private String userId;

    @NotBlank(message = "사용자 이름을 꼭 입력해주세요.")
    private String name;

    @NotBlank(message = "사용자 패스워드를 꼭 입력해주세요.")
    private String password;

    private String[] isAdmin;

    public User toEntity(){

        return User.builder().userId(userId).name(name).password(password).auth(
                isAdmin == null ? "ROLE_USER":"ROLE_ADMIN"
        ).enabled("1").build();
    }
}
