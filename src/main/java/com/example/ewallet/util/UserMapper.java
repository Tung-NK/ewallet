package com.example.ewallet.util;

import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.entity.User;

import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class UserMapper {

    private UserMapper() {

    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus().name(),
                user.getCreatedAt()
                        .toInstant(ZoneOffset.UTC)
                        .truncatedTo(ChronoUnit.SECONDS)
        );
    }
}
