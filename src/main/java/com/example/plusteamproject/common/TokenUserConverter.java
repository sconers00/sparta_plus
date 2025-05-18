package com.example.plusteamproject.common;

import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;
import com.example.plusteamproject.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenUserConverter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public User getUser(String token) {
        String email = jwtUtil.extractEmail(token);
        return userRepository.findByEmailOrElseThrow(email);
    }
}
