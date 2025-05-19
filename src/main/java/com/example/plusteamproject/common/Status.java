package com.example.plusteamproject.common;

import lombok.Getter;

//soft delete Enum
@Getter
public enum Status {
    EXIST(false), NON_EXIST(true);

    private final boolean value;

    Status(boolean value) {
        this.value = value;
    }

}
