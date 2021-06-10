package com.joypatel.smalltasks.task.dtos;

import com.joypatel.smalltasks.common.domain.AbstractResponse;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class TaskResponse extends AbstractResponse {

    private String subject;
    private String description;
    private Integer originPincode;
}
