package com.example.plusteamproject.domain.user.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> createUser(@PathVariable @Valid CreateUserRequestDto requestDto) {

        userService.createUser(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("회원가입이 완료되었습니다."));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<String>> login() {
        return null;
    }


}
