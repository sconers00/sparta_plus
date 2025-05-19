package com.example.plusteamproject.domain.user.dto.response;

import com.example.plusteamproject.domain.user.entity.User;
import lombok.Getter;

@Getter
public class FindUserResponseDto {

    private final String email;

    private final String name;

    private final String nickname;

    private final String phone;

    public FindUserResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
    }
}
