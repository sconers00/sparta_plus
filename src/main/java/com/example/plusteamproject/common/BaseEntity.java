<<<<<<<< HEAD:src/main/java/com/example/plusteamproject/common/commonEntity/BaseEntity.java
package com.example.plusteamproject.common.commonEntity;
========
package com.example.plusteamproject.common;
>>>>>>>> ec2a7713eaec8b434a1ce85e1029db7cba992190:src/main/java/com/example/plusteamproject/common/BaseEntity.java

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedDate // 생성시 자동입력
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}

