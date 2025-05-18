package com.example.plusteamproject.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserRequestDto {

    private final String email;

    private final String password;
}
