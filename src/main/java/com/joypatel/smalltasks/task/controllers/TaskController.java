package com.joypatel.smalltasks.task.controllers;

import com.joypatel.smalltasks.task.dtos.TaskCreationForm;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
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
    public List<TaskResponse> retrieveTasks(
            @RequestParam Optional<Integer> pincode,
            @RequestParam Optional<Integer> creatorId,
            @RequestParam Optional<Integer> executorId,
            @RequestParam Optional<Task.Status> status,
            @RequestParam(defaultValue = "0") Integer beyondId,
            @RequestParam(defaultValue = "10") Integer itemCount,
            @RequestParam(defaultValue = "true") Boolean next) {

        return taskRetrievalService.getTasks(pincode, creatorId, executorId, status, beyondId, next, itemCount);
    }
}
