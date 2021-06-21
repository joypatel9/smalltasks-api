package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.BusinessException;
import com.joypatel.smalltasks.common.MyError;
import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class TaskAssignmentService {

    private final TaskRepository taskRepository;
    private final UserCatalogService userCatalogService;
    private final MyUtils utils;

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public void assignTask(Integer taskId) {

        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                BusinessException.builder()
                        .responseStatus(HttpStatus.NOT_FOUND)
                        .error(MyError.of("task", "notFound", utils.getMessage("notFound")))
                        .build()
        );

        if (!task.isOpen())
            throw BusinessException.builder()
                    .responseStatus(HttpStatus.BAD_REQUEST)
                    .error(MyError.of("task", "taskNotOpen", utils.getMessage("taskNotOpen")))
                    .build();

        User user = userCatalogService.getCurrentUser();
        log.info("Assigning task {} to user {}", task.getId(), user.getId());

        task.setStatus(Task.Status.ASSIGNED);
        task.setExecutor(user);

        taskRepository.save(task);
        log.info("Assigned task {} to user {}", task.getId(), user.getId());
    }
}
