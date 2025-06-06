package com.example.plusteamproject.domain.user.entity;

import com.example.plusteamproject.common.BaseEntity;
import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role userRole;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean isDeleted = Status.EXIST.isValue();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User(CreateUserRequestDto requestDto, String password) {
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.nickname = requestDto.getNickname();
        this.password = password;
        this.phone = requestDto.getPhone();
        this.userRole = Role.valueOf(requestDto.getUserRole());
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateDeleteStatus(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
