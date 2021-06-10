package com.joypatel.smalltasks.task.controllers;

import com.joypatel.smalltasks.task.dtos.TaskCreationForm;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.services.TaskCreationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskCreationService taskCreationServiceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody TaskCreationForm form) {
        return taskCreationServiceService.create(form);
    }

}
