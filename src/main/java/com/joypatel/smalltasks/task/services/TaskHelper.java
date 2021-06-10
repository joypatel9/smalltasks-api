package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskHelper {
    TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .ref(task.getRef())
                .subject(task.getSubject())
                .description(task.getDescription())
                .originPincode(task.getOriginPincode())
                .build();
    }
}
