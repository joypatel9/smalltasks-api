package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.BusinessException;
import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final MyUtils utils;

    public TaskResponse toResponse(Task task) {

        if (task == null)
            return null;

        UserResponse creatorResponse = userService.toResponse(task.getCreator());
        UserResponse executorResponse = userService.toResponse(task.getExecutor());

        return TaskResponse.builder()
                .ref(task.getRef())
                .subject(task.getSubject())
                .description(task.getDescription())
                .originPincode(task.getOriginPincode())
                .creator(creatorResponse)
                .executor(executorResponse)
                .status(task.getStatus())
                .build();
    }

    public List<TaskResponse> toResponseList(List<Task> tasks) {

        return tasks.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public Task getTaskById(Integer taskId) {
        return taskRepository.findById(taskId).orElseThrow(() ->
                BusinessException.builder()
                        .responseStatus(HttpStatus.NOT_FOUND)
                        .error(utils.getError("task", "notFound"))
                        .build());
    }
}
