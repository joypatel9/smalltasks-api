package com.joypatel.smalltasks.task.dtos;

import com.joypatel.smalltasks.common.domain.AbstractResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class TaskResponse extends AbstractResponse {

    private UserResponse creator;
    private String subject;
    private String description;
    private Integer originPincode;
    private UserResponse executor;
    private Task.Status status;
}
