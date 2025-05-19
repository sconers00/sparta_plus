package com.example.plusteamproject.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserRequestDto {

    @Size(min = 2, max = 5, message = "이름은 최소 두 글자 최대 다섯 글자입니다.")
    private final String name;

    @Size(min = 2, max = 5, message = "닉네임은 최소 두 글자 최대 다섯 글자입니다.")
    private final String nickname;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "비밀번호는 8~20자의 영문과 숫자를 포함해야 합니다.")
    private final String password;

    @Pattern(regexp = "^010\\d{8}$", message = "올바르지 않은 전화번호입니다.")
    private final String phone;
}
