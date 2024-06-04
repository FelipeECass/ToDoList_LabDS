package com.labdesoft.roteiro1.controller;

import com.labdesoft.roteiro1.entity.DeadLineTask;
import com.labdesoft.roteiro1.service.DeadLineTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeadLineTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeadLineTaskService deadLineTaskService;

    private List<DeadLineTask> deadLineTasks;

    @BeforeEach
    public void setup() {
        DeadLineTask task1 = new DeadLineTask();
        task1.setId(1);
        task1.setTaskTitle("Test Task 1");
        task1.setTaskStatus("Pending");
        task1.setTaskDeadLine(-2); // 2 days overdue

        DeadLineTask task2 = new DeadLineTask();
        task2.setId(2);
        task2.setTaskTitle("Test Task 2");
        task2.setTaskStatus("Completed");
        task2.setTaskDeadLine(5); // 5 days remaining

        deadLineTasks = Arrays.asList(task1, task2);
    }

    @Test
    public void listAll_shouldReturnAllTasks() throws Exception {
        Mockito.when(deadLineTaskService.getAllTask()).thenReturn(deadLineTasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/deadLineTask")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskTitle", is("Test Task 1")))
                .andExpect(jsonPath("$[1].taskTitle", is("Test Task 2")))
                .andExpect(jsonPath("$[0].taskStatus", containsString("2 dias de atraso")))
                .andExpect(jsonPath("$[1].taskStatus", is("Completed")));
    }

    @Test
    public void create_shouldCreateNewTask() throws Exception {
        DeadLineTask v_newTask = new DeadLineTask();
        v_newTask.setTaskTitle("New Task");
        v_newTask.setTaskDesc("New Task Test");
        v_newTask.setTaskStatus("Pending");
        v_newTask.setTaskDeadLine(3); // 3 days remaining

        Mockito.when(deadLineTaskService.createTask(Mockito.any(DeadLineTask.class))).thenReturn("Task created");

        mockMvc.perform(MockMvcRequestBuilders.post("/deadLineTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskTitle", v_newTask.getTaskTitle())
                        .param("taskDesc", v_newTask.getTaskDesc())
                        .param("taskStatus", v_newTask.getTaskStatus())
                        .param("taskDeadLine", v_newTask.getTaskDeadLine().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Task created"));
    }

    @Test
    public void edit_shouldEditTaskStatus() throws Exception {
        DeadLineTask v_updatedTask = new DeadLineTask();
        v_updatedTask.setId(1);
        v_updatedTask.setTaskTitle("Updated Task");
        v_updatedTask.setTaskStatus("In Progress");
        v_updatedTask.setTaskDeadLine(1); // 1 day remaining

        Mockito.when(deadLineTaskService.editTask(Mockito.any(DeadLineTask.class))).thenReturn("Task updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/deadLineTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskId", String.valueOf(v_updatedTask.getId()))
                        .param("taskTitle", v_updatedTask.getTaskTitle())
                        .param("taskStatus", v_updatedTask.getTaskStatus())
                        .param("taskDeadLine", v_updatedTask.getTaskDeadLine().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated"));
    }
}
