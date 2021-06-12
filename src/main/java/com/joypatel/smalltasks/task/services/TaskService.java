package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    TaskResponse toResponse(Task task) {

        if (task == null)
            return null;

        UserResponse creator = userService.toResponse(task.getCreator());
        UserResponse executor = userService.toResponse(task.getExecutor());

        return TaskResponse.builder()
                .ref(task.getRef())
                .subject(task.getSubject())
                .description(task.getDescription())
                .originPincode(task.getOriginPincode())
                .creator(creator)
                .executor(executor)
                .status(task.getStatus())
                .build();
    }

    public List<TaskResponse> toResponse(List<Task> tasks) {

        return tasks.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public List<Task> findOpenTasks(Integer pincode, User currentUser) {
        return taskRepository.findTasks(pincode, currentUser, Task.Status.OPEN);
    }
}
