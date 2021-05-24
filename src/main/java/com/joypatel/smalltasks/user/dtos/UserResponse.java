package com.joypatel.smalltasks.user.dtos;

import com.joypatel.smalltasks.common.domain.AbstractResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class UserResponse extends AbstractResponse {

    private String mobile;
    private String name;
}
