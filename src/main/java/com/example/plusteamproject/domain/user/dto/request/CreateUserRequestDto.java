package com.example.plusteamproject.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 5, message = "이름은 최소 두 글자 최대 다섯 글자입니다.")
    private final String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 5, message = "닉네임은 최소 두 글자 최대 다섯 글자입니다.")
    private final String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "비밀번호는 8~20자의 영문과 숫자를 포함해야 합니다.")
    private final String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바르지 않은 전화번호입니다.")
    private final String phone;

    @NotBlank(message = "사용자 역할을 입력해주세요.")
    private final String userRole;
}
