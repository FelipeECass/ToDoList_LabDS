package com.labdesoft.roteiro1.controller;

import com.labdesoft.roteiro1.entity.DateTask;
import com.labdesoft.roteiro1.service.DateTaskService;
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
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DateTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DateTaskService dateTaskService;

    private List<DateTask> dateTasks;

    @BeforeEach
    public void setup() {
        DateTask task1 = new DateTask();
        task1.setId(1);
        task1.setTaskTitle("Test Task 1");
        task1.setTaskStatus("Pending");
        task1.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago

        DateTask task2 = new DateTask();
        task2.setId(2);
        task2.setTaskTitle("Test Task 2");
        task2.setTaskStatus("Completed");
        task2.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() + 86400000)); // 1 day in future

        dateTasks = Arrays.asList(task1, task2);
    }

    @Test
    public void listAll_shouldReturnAllTasks() throws Exception {
        Mockito.when(dateTaskService.getAllTask()).thenReturn(dateTasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/dateTask")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskName", is("Test Task 1")))
                .andExpect(jsonPath("$[1].taskName", is("Test Task 2")))
                .andExpect(jsonPath("$[0].taskStatus", containsString("dias de atraso")))
                .andExpect(jsonPath("$[1].taskStatus", is("Completed")));
    }

    @Test
    public void create_shouldCreateNewTask() throws Exception {
        DateTask newTask = new DateTask();
        newTask.setTaskTitle("New Task");
        newTask.setTaskStatus("Pending");
        newTask.setTaskDateEstimatedConclusion(new Date());

        Mockito.when(dateTaskService.createTask(Mockito.any(DateTask.class))).thenReturn("Task created");

        mockMvc.perform(MockMvcRequestBuilders.post("/dateTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskName", newTask.getTaskTitle())
                        .param("taskStatus", newTask.getTaskStatus())
                        .param("taskDateEstimatedConclusion", String.valueOf(newTask.getTaskDateEstimatedConclusion().getTime())))
                .andExpect(status().isOk())
                .andExpect(content().string("Task created"));
    }

    @Test
    public void edit_shouldEditTaskStatus() throws Exception {
        DateTask updatedTask = new DateTask();
        updatedTask.setId(1) ;
        updatedTask.setTaskTitle("Updated Task");
        updatedTask.setTaskStatus("In Progress");
        updatedTask.setTaskDateEstimatedConclusion(new Date());

        Mockito.when(dateTaskService.editTask(Mockito.any(DateTask.class))).thenReturn("Task updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/dateTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskId", String.valueOf(updatedTask.getId()))
                        .param("taskName", updatedTask.getTaskTitle())
                        .param("taskStatus", updatedTask.getTaskStatus())
                        .param("taskDateEstimatedConclusion", String.valueOf(updatedTask.getTaskDateEstimatedConclusion().getTime())))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated"));
    }
}
