package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Test
    void TaskHelper_toResponse() {

        //given
        User user = new User();

        Task task = new Task();
        task.setRef("some-ref");
        task.setSubject("This is the task's subject");
        task.setDescription("This is the task's description");
        task.setOriginPincode(723093);
        task.setExecutor(user);

        //when
        TaskResponse response = taskService.toResponse(task);

        //then
        assertEquals(task.getRef(), response.getRef());
        assertEquals(task.getSubject(), response.getSubject());
        assertEquals(task.getDescription(), response.getDescription());
        assertEquals(task.getOriginPincode(), response.getOriginPincode());
        assertEquals(task.getExecutor(), response.getExecutor());
    }
}
