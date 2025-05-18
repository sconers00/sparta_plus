package com.example.plusteamproject.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class LoginUserRequestDto {

    private final String email;

    private final String password;
}
