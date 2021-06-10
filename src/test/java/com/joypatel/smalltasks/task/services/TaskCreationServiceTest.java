package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.dtos.TaskCreationForm;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskCreationServiceTests {

    private final TaskCreationForm form = new TaskCreationForm();
    private final TaskResponse response = TaskResponse.builder().build();

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskHelper taskHelper;

    @Mock
    private MyUtils utils;

    @InjectMocks
    private TaskCreationService service;

    @Captor
    private ArgumentCaptor<Task> taskCaptor;

    @BeforeEach
    void setUp() {
        form.setSubject("This is the task's subject");
        form.setDescription("This is the task's description");
        form.setOriginPincode(734278);
    }

    @Test
    void testCreate() {

        //given
        String ref = "some-ref";

        when(utils.newUid()).thenReturn(ref);
        when(taskHelper.toResponse(any(Task.class))).thenReturn(response);

        //when
        TaskResponse r = service.create(form);

        //then
        assertEquals(response, r);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();
        verify(taskHelper).toResponse(task);

        assertEquals(ref, task.getRef());
        assertEquals(form.getSubject(), task.getSubject());
        assertEquals(form.getDescription(), task.getDescription());
        assertEquals(form.getOriginPincode(), task.getOriginPincode());
    }
}
