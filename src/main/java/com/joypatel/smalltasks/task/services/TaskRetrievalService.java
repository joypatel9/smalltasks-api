package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TaskRetrievalService {

    private final TaskRepository taskRepository;
    private final TaskService taskService;
    public UserCatalogService userCatalogService;

    @PreAuthorize("isAuthenticated()")
    public List<TaskResponse> getTasks(Optional<Integer> optionalPincode) {

        log.info("Getting tasks with originPincode as : {}", optionalPincode);

        Integer pincode = optionalPincode.orElseGet(() ->
                userCatalogService.getCurrentUser().getPincode());

        List<TaskResponse> taskResponses = taskService.toResponse(
                taskService.findOpenTasks(pincode, userCatalogService.getCurrentUser()));

        return taskResponses;
    }
}
