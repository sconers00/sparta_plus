package com.example.plusteamproject.domain.user.entity;

import com.example.plusteamproject.common.BaseEntity;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String password;

    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    public User(CreateUserRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getPassword();
        this.phone = requestDto.getPhone();
    }
}
