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

import static com.joypatel.smalltasks.task.entities.Task.Status.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskCreationServiceTests {

    private final TaskCreationForm form = new TaskCreationForm();

    private final TaskResponse response = TaskResponse.builder().build();
    String ref = "some-ref";
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskService taskService;
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
        form.setSubject("This is the task's subject");
        form.setDescription("This is the task's description");

        when(utils.newUid()).thenReturn(ref);
        when(taskService.toResponse(any(Task.class))).thenReturn(response);
    }

    private Task commonTests(TaskResponse taskResponse) {

        assertEquals(response, taskResponse);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();
        verify(taskService).toResponse(task);

        assertEquals(ref, task.getRef());
        assertEquals(form.getSubject(), task.getSubject());
        assertEquals(form.getDescription(), task.getDescription());
        assertEquals(OPEN, task.getStatus());

        return task;
    }

    @Test
    void test_create_task_with_originPincode() {
        // given
        form.setOriginPincode(723482);

        // when
        TaskResponse taskResponse = service.create(form);

        // then
        Task task = commonTests(taskResponse);
        assertEquals(form.getOriginPincode(), task.getOriginPincode());
    }

    @Test
    void test_create_task_without_originPincode() {
        //given

        //current user
        User user = new User();
        user.setMobile("9334778738");
        user.setName("Joy Patel");
        user.setPassword("password");
        user.setPincode(751024);

        when(userCatalogService.getCurrentUser()).thenReturn(user);

        //when
        TaskResponse taskResponse = service.create(form);

        //then
        Task task = commonTests(taskResponse);
        assertEquals(user.getPincode(), task.getOriginPincode());
    }
}
