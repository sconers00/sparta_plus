package com.example.plusteamproject.domain.user.service;

import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.dto.request.LoginUserRequestDto;
import com.example.plusteamproject.domain.user.dto.request.UpdateUserRequestDto;
import com.example.plusteamproject.domain.user.dto.response.FindUserResponseDto;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;
import com.example.plusteamproject.jwt.JwtUtil;
import com.example.plusteamproject.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //회원가입
    @Transactional
    public void createUser(CreateUserRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto, password);
        userRepository.save(user);
    }

    //로그인
    @Transactional(readOnly = true)
    public String loginUser(LoginUserRequestDto requestDto) {

        User user = userRepository.findByEmailOrElseThrow(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getUserRole());
    }

    //사용자 정보 조회
    @Transactional(readOnly = true)
    public FindUserResponseDto findUserByToken(CustomUserDetail userDetail) {
        User user = userDetail.getUser();

        return new FindUserResponseDto(user);
    }

    //사용자 정보 수정
    @Transactional
    public void updateUser(CustomUserDetail userDetail, UpdateUserRequestDto requestDto) {

        User user = userDetail.getUser();

        if (requestDto.getName() != null) {
            user.setName(requestDto.getName());
        }

        if (requestDto.getNickname() != null) {
            user.setNickname(requestDto.getNickname());
        }

        if (requestDto.getPassword() != null) {
            user.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        if (requestDto.getPhone() != null) {
            user.setPhone(requestDto.getPhone());
        }

        userRepository.save(user);
    }

    //사용자 삭제
    @Transactional
    public void deleteUser(CustomUserDetail userDetail) {

        User user = userDetail.getUser();
        userRepository.delete(user);

    }
}
