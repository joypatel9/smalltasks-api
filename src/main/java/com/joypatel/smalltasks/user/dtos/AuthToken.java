package com.joypatel.smalltasks.user.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AuthToken {
    private final String token;
}
