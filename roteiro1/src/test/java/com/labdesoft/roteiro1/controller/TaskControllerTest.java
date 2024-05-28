package com.labdesoft.roteiro1.controller;

import com.labdesoft.roteiro1.entity.Task;
import com.labdesoft.roteiro1.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private List<Task> tasks;

    @BeforeEach
    public void setup() {
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskTitle("Test Task 1");
        task1.setTaskStatus("Pending");

        Task task2 = new Task();
        task2.setId(2);
        task2.setTaskTitle("Test Task 2");
        task2.setTaskStatus("Completed");

        tasks = Arrays.asList(task1, task2);
    }

    @Test
    public void listAll_shouldReturnAllTasks() throws Exception {
        Mockito.when(taskService.getAllTask()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskName", is("Test Task 1")))
                .andExpect(jsonPath("$[1].taskName", is("Test Task 2")));
    }

    @Test
    public void create_shouldCreateNewTask() throws Exception {
        Task newTask = new Task();
        newTask.setTaskTitle("New Task");
        newTask.setTaskStatus("Pending");

        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn("Task created");

        mockMvc.perform(MockMvcRequestBuilders.post("/task")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskName", newTask.getTaskTitle())
                        .param("taskStatus", newTask.getTaskStatus()))
                .andExpect(status().isOk())
                .andExpect(content().string("Task created"));
    }

    @Test
    public void edit_shouldEditTaskStatus() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTaskTitle("Updated Task");
        updatedTask.setTaskStatus("In Progress");

        Mockito.when(taskService.editTask(Mockito.any(Task.class))).thenReturn("Task updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/task")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskId", String.valueOf(updatedTask.getId()))
                        .param("taskName", updatedTask.getTaskTitle())
                        .param("taskStatus", updatedTask.getTaskStatus()))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated"));
    }
}
