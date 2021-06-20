package com.joypatel.smalltasks.task.controllers;

import com.joypatel.smalltasks.task.services.TaskAssignmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskAssignmentController {

    TaskAssignmentService taskAssignmentService;

    @PostMapping("/{taskId}/executor")
    public void assignTask(@PathVariable Integer taskId) {
        taskAssignmentService.assignTask(taskId);
    }
}
