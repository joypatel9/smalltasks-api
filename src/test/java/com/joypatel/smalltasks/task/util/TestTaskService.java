package com.joypatel.smalltasks.task.util;

import com.joypatel.smalltasks.task.entities.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TestTaskService {

    private final TestTaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Task.Status getTaskStatus(Integer id) {
        return taskRepository.getById(id).getStatus();
    }
}
