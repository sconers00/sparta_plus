package com.example.plusteamproject.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteReviewRequestDto {

    @NotBlank(message = "비밀번호는 필수값입니다.")
    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@_\\-#$]).{8,20}$", message = "비밀번호는 8~20자 사이이며 숫자, 영문자, 특수문자(@_-#$)를 각각 포함해야 합니다.")
    private final String password;
}
