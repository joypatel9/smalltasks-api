package com.joypatel.smalltasks.task.controllers;

import com.joypatel.smalltasks.task.dtos.TaskCreationForm;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.services.TaskCreationService;
import com.joypatel.smalltasks.task.services.TaskRetrievalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskCreationService taskCreationServiceService;
    private final TaskRetrievalService taskRetrievalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody TaskCreationForm form) {
        return taskCreationServiceService.create(form);
    }

    @GetMapping
    public List<TaskResponse> retrieveTasks(@RequestParam Optional<Integer> pincode) {
        return taskRetrievalService.getTasks(pincode);
    }
}
