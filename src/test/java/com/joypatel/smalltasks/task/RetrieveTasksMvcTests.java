package com.joypatel.smalltasks.task;

import com.joypatel.smalltasks.AbstractMvcTests;
import com.joypatel.smalltasks.task.entities.Task;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.joypatel.smalltasks.user.TestUtils.MOBILE;
import static com.joypatel.smalltasks.user.TestUtils.PASSWORD;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrieveTasksMvcTests extends AbstractMvcTests {

    final Integer pincode = 948292;
    final Integer creatorId = 3;
    final Integer executorId = 5;
    final Task.Status status = Task.Status.OPEN;
    final Integer beyondId = 4;
    final Integer itemCount = 2;
    final Boolean next = true;

    @Test
    @Sql({"classpath:itest/user/sql/user.sql",
            "classpath:itest/task/sql/task.sql"})
    void retrieveTasks() throws Exception {

        // given
        var token = loginAndGetToken(MOBILE, PASSWORD);

        // when
        mockMvc.perform(get("/tasks")
                .header("Authorization", "Bearer " + token)
                .param("pincode", String.valueOf(pincode))
                .param("creatorId", String.valueOf(creatorId))
                .param("executorId", String.valueOf(executorId))
                .param("status", String.valueOf(status))
                .param("beyondId", String.valueOf(beyondId))
                .param("itemCount", String.valueOf(itemCount))
                .param("next", String.valueOf(next)))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ref").value("task-ref-5"))
                .andExpect(jsonPath("$[0].subject").value("This is the subject"))
                .andExpect(jsonPath("$[0].description").value("This is the description"))
                .andExpect(jsonPath("$[0].originPincode").value(948292))
                .andExpect(jsonPath("$[0].creator.ref").value("user-ref-3"))
                .andExpect(jsonPath("$[0].executor.ref").value("user-ref-5"));
    }

    @Test
    void retrieveTasksWithoutLogin() throws Exception {

        // when
        mockMvc.perform(get("/tasks")
                .param("pincode", String.valueOf(pincode))
                .param("beyondId", String.valueOf(beyondId))
                .param("itemCount", String.valueOf(itemCount))
                .param("next", String.valueOf(next)))

                // then
                .andExpect(status().isForbidden());
    }

}
