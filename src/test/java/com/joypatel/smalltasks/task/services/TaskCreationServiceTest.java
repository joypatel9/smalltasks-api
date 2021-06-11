package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.dtos.TaskCreationForm;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserCatalogService;
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

    private final TaskCreationForm form1 = new TaskCreationForm();
    private final TaskCreationForm form2 = new TaskCreationForm();

    private final TaskResponse response = TaskResponse.builder().build();

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskHelper taskHelper;

    @Mock
    private MyUtils utils;

    @Mock
    private UserCatalogService userCatalogService;

    @InjectMocks
    private TaskCreationService service;

    @Captor
    private ArgumentCaptor<Task> taskCaptor;

    @BeforeEach
    void setUp() {
        //form with originPincode
        form1.setSubject("This is the task's subject");
        form1.setDescription("This is the task's description");
        form1.setOriginPincode(734278);

        //form without originPincode
        form2.setSubject("This is the task's subject");
        form2.setDescription("This is the task's description");
    }

    @Test
    void test_create_task_with_originPincode() {

        //given
        String ref = "some-ref";

        when(utils.newUid()).thenReturn(ref);
        when(taskHelper.toResponse(any(Task.class))).thenReturn(response);

        //when
        TaskResponse r = service.create(form1);

        //then
        assertEquals(response, r);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();
        verify(taskHelper).toResponse(task);

        assertEquals(ref, task.getRef());
        assertEquals(form1.getSubject(), task.getSubject());
        assertEquals(form1.getDescription(), task.getDescription());
        assertEquals(form1.getOriginPincode(), task.getOriginPincode());
    }

    @Test
    void test_create_task_without_originPincode() {

        //given
        String ref = "some-ref";

        when(utils.newUid()).thenReturn(ref);
        when(taskHelper.toResponse(any(Task.class))).thenReturn(response);

        //creating current user
        User user = new User();
        user.setMobile("9334778738");
        user.setName("Joy Patel");
        user.setPassword("password");
        user.setPincode(751024);

        when(userCatalogService.getCurrentUser()).thenReturn(user);

        //when
        TaskResponse r = service.create(form2);

        //then
        assertEquals(response, r);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();
        verify(taskHelper).toResponse(task);

        assertEquals(ref, task.getRef());
        assertEquals(form2.getSubject(), task.getSubject());
        assertEquals(form2.getDescription(), task.getDescription());
        assertEquals(751024, task.getOriginPincode());
    }
}
