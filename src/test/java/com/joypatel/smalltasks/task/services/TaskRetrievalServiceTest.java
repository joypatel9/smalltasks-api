package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.QTask;
import com.joypatel.smalltasks.task.entities.Task;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(MockitoExtension.class)
class TaskRetrievalServiceTest {

    final int beyondId = 1;
    final int itemCount = 2;

    final Integer pincode = 751024;
    final Integer creatorId = 1290;
    final Integer executorId = 3422;
    final Task.Status status = Task.Status.OPEN;

    private final Page<Task> page = mock(Page.class);

    private final List<TaskResponse> expectedResponses = List.of(TaskResponse.builder().build());
    private final List<Task> taskList = List.of(new Task());

    @Captor
    private ArgumentCaptor<Predicate> criteriaCaptor;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskRetrievalService service;

    @BeforeEach
    void setUp() {

        when(taskRepository.findAll(any(Predicate.class), any(PageRequest.class))).thenReturn(page);
        when(page.toList()).thenReturn(taskList);
        when(taskService.toResponseList(taskList)).thenReturn(expectedResponses);
    }

    @Test
    void getTasks() {

        // when
        Pageable pageable = PageRequest.of(0, itemCount, DESC, "id");
        List<TaskResponse> actualResponses = service.getTasks(Optional.of(pincode),
                Optional.of(creatorId), Optional.of(executorId), Optional.of(status),
                beyondId, false, itemCount);

        // then
        verify(taskRepository).findAll(criteriaCaptor.capture(), eq(pageable));

        QTask task = QTask.task;
        assertEquals(new BooleanBuilder()
                        .and(task.originPincode.eq(pincode))
                        .and(task.creator.id.eq(creatorId))
                        .and(task.executor.id.eq(executorId))
                        .and(task.status.eq(status))
                        .and(task.id.lt(beyondId)),
                criteriaCaptor.getValue());

        assertEquals(expectedResponses, actualResponses);
    }

    @Test
    void getTasks_When_pincodeAndCreatorAreGiven() {

        // when
        Pageable pageable = PageRequest.of(0, itemCount, ASC, "id");
        List<TaskResponse> actualResponses = service.getTasks(Optional.of(pincode),
                Optional.of(creatorId), Optional.empty(), Optional.empty(),
                beyondId, true, itemCount);

        // then
        verify(taskRepository).findAll(criteriaCaptor.capture(), eq(pageable));

        QTask task = QTask.task;
        assertEquals(new BooleanBuilder()
                        .and(task.originPincode.eq(pincode))
                        .and(task.creator.id.eq(creatorId))
                        .and(task.id.gt(beyondId)),
                criteriaCaptor.getValue());

        assertEquals(expectedResponses, actualResponses);
    }
}
