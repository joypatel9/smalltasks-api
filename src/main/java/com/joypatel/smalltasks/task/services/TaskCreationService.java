package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.dtos.TaskCreationForm;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@AllArgsConstructor
@Slf4j
public class TaskCreationService {

    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final UserCatalogService userCatalogService;
    private final MyUtils utils;

    @PreAuthorize("isAuthenticated()")
    public TaskResponse create(@Valid TaskCreationForm form) {

        log.info("Creating task {}", form);

        Task task = toTask(form);
        taskRepository.save(task);

        TaskResponse response = taskService.toResponse(task);
        log.info("Created task {}", response);
        return response;
    }

    private Task toTask(TaskCreationForm form) {

        Task task = new Task();
        task.setCreator(userCatalogService.getCurrentUser());
        task.setRef(utils.newUid());
        task.setSubject(form.getSubject());
        task.setDescription(form.getDescription());
        task.setStatus(Task.Status.OPEN);

        task.setOriginPincode(form.getOriginPincode() == null ? userCatalogService.getCurrentUser().getPincode() : form.getOriginPincode());
        return task;
    }
}
