package com.example.plusteamproject.domain.user.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.dto.request.LoginUserRequestDto;
import com.example.plusteamproject.domain.user.dto.request.UpdateUserRequestDto;
import com.example.plusteamproject.domain.user.dto.response.FindUserResponseDto;
import com.example.plusteamproject.domain.user.service.UserService;
import com.example.plusteamproject.security.CustomUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param requestDto 사용자 회원가입 요청 정보
     * @return 회원가입 성공 메시지
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody @Valid CreateUserRequestDto requestDto) {

        userService.createUser(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("회원가입이 완료되었습니다."));
    }

    /**
     * 로그인
     * @param requestDto 로그인 요청 정보
     * @return 로그인 성공 메시지, 토큰
     */
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginUserRequestDto requestDto) {

        String token = userService.loginUser(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("로그인에 성공했습니다.", token));
    }

    /**
     * 로그인한 사용자 정보 조회
     * @param userDetail 인증된 사용자 정보
     * @return 조회 성공 메시지, 사용자 정보 ApiResponse
     */
    @GetMapping
    public ResponseEntity<ApiResponse<FindUserResponseDto>> findUser(@AuthenticationPrincipal CustomUserDetail userDetail) {

        FindUserResponseDto responseDto = userService.findUserByToken(userDetail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("정보를 조회합니다.", responseDto));
    }

    /**
     * 로그인한 사용자 정보 수정
     * @param userDetail 인증된 사용자 정보
     * @param requestDto 수정할 사용자 정보
     * @return 정보 수정 성공 메시지
     */
    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateUser(@AuthenticationPrincipal CustomUserDetail userDetail, @RequestBody UpdateUserRequestDto requestDto) {

        userService.updateUser(userDetail, requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("회원 정보 수정에 성공했습니다."));
    }

    /**
     * 로그인한 사용자 삭제
     * @param userDetail 인증된 사용자 정보
     * @return 삭제 성공 메시지
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetail userDetail) {

        userService.deleteUser(userDetail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("회원 삭제에 성공했습니다."));
    }

}
