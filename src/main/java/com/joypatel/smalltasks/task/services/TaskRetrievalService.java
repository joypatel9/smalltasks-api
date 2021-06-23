package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.QTask;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
@Slf4j
public class TaskRetrievalService {

    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final UserCatalogService userCatalogService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(Optional<Integer> optionalPincode,
                                       Optional<Integer> optionalCreatorId,
                                       Optional<Integer> optionalExecutorId,
                                       Optional<Task.Status> optionalStatus,
                                       int beyondId,
                                       boolean next,
                                       int itemCount) {

        QTask task = QTask.task;
        BooleanBuilder criteria = new BooleanBuilder();
        optionalPincode.ifPresent(pincode -> criteria.and(task.originPincode.eq(pincode)));
        optionalCreatorId.ifPresent(creatorId -> criteria.and(task.creator.id.eq(creatorId)));
        optionalExecutorId.ifPresent(executorId -> criteria.and(task.executor.id.eq(executorId)));
        optionalStatus.ifPresent(status -> criteria.and(task.status.eq(status)));

        criteria.and(next ? task.id.gt(beyondId) : task.id.lt(beyondId));
        Pageable pageable = PageRequest.of(0, itemCount, next ? ASC : DESC, "id");
        List<Task> tasks = taskRepository.findAll(criteria, pageable).toList();
        List<TaskResponse> taskResponses = taskService.toResponseList(tasks);

        log.info("Got tasks with originPincode={}, optionalCreatorId={}, " +
                        "optionalExecutorId={}, optionalStatus={}, beyondId={}, " +
                        "next={}, itemCount={}: {}",
                optionalPincode, optionalCreatorId, optionalExecutorId,
                optionalStatus, beyondId, next, itemCount, taskResponses);

        return taskResponses;
    }
}
