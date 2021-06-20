package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TaskRetrievalService {

    private final TaskService taskService;
    private final UserCatalogService userCatalogService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(Optional<Integer> optionalPincode, int beyondId, boolean next, int itemCount) {

        Integer pincode = optionalPincode.orElseGet(() ->
                userCatalogService.getCurrentUser().getPincode());

        List<TaskResponse> taskResponses = taskService.toResponseList(
                taskService.findOpenTasks(pincode, userCatalogService.getCurrentUser(), beyondId, next, itemCount));

        log.info("Got tasks with originPincode={}, beyondId={}, next={}, itemCount={}: {}",
                pincode, beyondId, next, itemCount, taskResponses);

        return taskResponses;
    }
}
