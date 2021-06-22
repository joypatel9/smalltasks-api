package com.joypatel.smalltasks.task;

import com.joypatel.smalltasks.AbstractMvcTests;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.task.util.TestTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static com.joypatel.smalltasks.task.entities.Task.Status.CLOSED;
import static com.joypatel.smalltasks.user.TestUtils.MOBILE;
import static com.joypatel.smalltasks.user.TestUtils.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CloseTaskMvcTests extends AbstractMvcTests {

    @Autowired
    private TestTaskRepository taskRepository;

    @Test
    @Sql({"classpath:itest/user/sql/user.sql",
            "classpath:itest/task/sql/task.sql"})
    void closeTask() throws Exception {

        // given
        var token = loginAndGetToken(MOBILE, PASSWORD);
        Integer taskId = 2;

        // when
        mockMvc.perform(post("/tasks/{taskId}/closure", taskId)
                .header("Authorization", "Bearer " + token))

                // then
                .andExpect(status().isOk());

        Task task = taskRepository.getById(taskId);
        assertEquals(CLOSED, task.getStatus());
    }

    @Test
    void closeTasksWithoutLogin() throws Exception {

        // when
        mockMvc.perform(post("/tasks/89/closure"))

                // then
                .andExpect(status().isForbidden());
    }
}
