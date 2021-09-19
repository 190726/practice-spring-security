package com.sk.user.dto;

import com.sk.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String userId;
    private final String name;
    private final String password;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.password = user.getPassword();
    }
}
