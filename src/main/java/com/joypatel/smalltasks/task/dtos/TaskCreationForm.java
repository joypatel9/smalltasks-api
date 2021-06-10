package com.joypatel.smalltasks.task.dtos;

import com.joypatel.smalltasks.task.validation.ValidDescription;
import com.joypatel.smalltasks.task.validation.ValidSubject;
import com.joypatel.smalltasks.user.validation.ValidPincode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreationForm {

    @ValidSubject
    private String subject;

    @ValidDescription
    private String description;

    @ValidPincode
    private Integer originPincode;
}
