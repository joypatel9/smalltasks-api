package com.joypatel.smalltasks.task;

import com.joypatel.smalltasks.AbstractMvcTests;
import com.joypatel.smalltasks.task.util.TestTaskService;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.*;

import static com.joypatel.smalltasks.task.entities.Task.Status.ASSIGNED;
import static com.joypatel.smalltasks.user.TestUtils.MOBILE;
import static com.joypatel.smalltasks.user.TestUtils.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AssignTaskParallelyMvcTests extends AbstractMvcTests {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    private TestTaskService taskService;

    @SpyBean
    private UserCatalogService userCatalogService;

    @AfterEach
    void shutdownExecutor() {
        executor.shutdown();
    }

    @Test
    @Sql({"classpath:itest/user/sql/user.sql",
            "classpath:itest/task/sql/task.sql"})
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void assignTaskParallely_Should_ThrowOptimisticLockingFailureException() throws Exception {

        // given
        var token = loginAndGetToken(MOBILE, PASSWORD);
        Integer taskId = 6;
        CyclicBarrier barrier = new CyclicBarrier(2);

        doAnswer(invocation -> {
            barrier.await(5, TimeUnit.SECONDS);
            return invocation.callRealMethod();
        }).when(userCatalogService).getCurrentUser();

        Future<Integer> call1Future = executor.submit(() -> {
            try {
                var result = mockMvc.perform(post("/tasks/{taskId}/executor", taskId)
                        .header("Authorization", "Bearer " + token))
                        .andReturn();

                return result.getResponse().getStatus();

            } catch (Exception ex) {
                return -1;
            }
        });

        int call2Status = mockMvc.perform(post("/tasks/{taskId}/executor", taskId)
                .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getStatus();

        int call1Status = call1Future.get();

        assertTrue(call1Status == 409 || call2Status == 409);
        assertTrue(call1Status == 200 || call2Status == 200);
        assertEquals(ASSIGNED, taskService.getTaskStatus(taskId));
    }

}
