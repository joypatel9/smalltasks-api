package com.joypatel.smalltasks.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString
public abstract class AbstractResponse {

    private final String ref;
}
