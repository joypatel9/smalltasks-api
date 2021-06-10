package com.joypatel.smalltasks.task;

import com.joypatel.smalltasks.AbstractMvcTests;
import com.joypatel.smalltasks.common.JwtService;
import com.joypatel.smalltasks.task.entities.Task;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.joypatel.smalltasks.common.MyUtils.UUID_LEN;
import static com.joypatel.smalltasks.task.TestUtils.*;
import static com.joypatel.smalltasks.user.TestUtils.MOBILE;
import static com.joypatel.smalltasks.user.TestUtils.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTaskMvcTests extends AbstractMvcTests {

    @Autowired
    JwtService jwtService;

    @Autowired
    private TestTaskRepository repository;

    private String createTaskData;

    @Value("classpath:itest/task/payload/create-task.json")
    public void setCreateTaskData(Resource resource) throws IOException {
        createTaskData = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private String invalidData;

    @Value("classpath:itest/task/payload/create-task-invalid-data.json")
    public void setInvalidData(Resource resource) throws IOException {
        invalidData = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Test
    @Sql("classpath:itest/user/sql/user.sql")
    void createTask_When_InvalidData() throws Exception {

        // given
        var token = getToken(MOBILE, PASSWORD);

        // when
        mockMvc.perform(post("/tasks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidData))

                // then
                .andExpect(status().isUnprocessableEntity());

        List<Task> tasks = repository.findAll();
        assertEquals(0, tasks.size());
    }

    @Test
    @Sql("classpath:itest/user/sql/user.sql")
    void createTask() throws Exception {

        // given
        var token = getToken(MOBILE, PASSWORD);

        // when
        mockMvc.perform(post("/tasks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTaskData))

                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("ref").isString())
                .andExpect(jsonPath("subject").value(SUBJECT))
                .andExpect(jsonPath("description").value(DESCRIPTION))
                .andExpect(jsonPath("originPincode").value(ORIGIN_PINCODE));

        List<Task> tasks = repository.findAll();
        assertEquals(1, tasks.size());

        Task task = tasks.get(0);

        assertNotNull(task.getId());
        assertEquals(UUID_LEN, task.getRef().length());
        assertEquals(SUBJECT, task.getSubject());
        assertEquals(DESCRIPTION, task.getDescription());
        assertEquals(ORIGIN_PINCODE, task.getOriginPincode());
        assertEquals(MOBILE, task.getCreator().getMobile());
    }

    @Test
    void createTaskWithoutLogin() throws Exception {

        // when
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTaskData))

                // then
                .andExpect(status().isForbidden());

        List<Task> tasks = repository.findAll();
        assertEquals(0, tasks.size());
    }

}
