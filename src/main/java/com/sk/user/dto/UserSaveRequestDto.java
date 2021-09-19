package com.sk.user.dto;

import com.sk.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSaveRequestDto {

    private String userId;
    private String name;
    private String password;

    public User toEntity(){
        return User.builder().userId(userId).name(name).password(password).build();
    }
}
