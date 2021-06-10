package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskHelperTest {

    private final TaskHelper taskHelper = new TaskHelper();

    @Test
    void TaskHelper_toResponse() {

        //given
        Task task = new Task();
        task.setRef("some-ref");
        task.setSubject("This is the task's subject");
        task.setDescription("This is the task's description");
        task.setOriginPincode(723093);

        //when
        TaskResponse response = taskHelper.toResponse(task);

        //then
        assertEquals(task.getRef(), response.getRef());
        assertEquals(task.getSubject(), response.getSubject());
        assertEquals(task.getDescription(), response.getDescription());
        assertEquals(task.getOriginPincode(), response.getOriginPincode());
    }
}
