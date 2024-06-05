package com.labdesoft.controller;

import com.labdesoft.entity.DateTask;
import com.labdesoft.service.DateTaskService;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DateTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DateTaskService dateTaskService;

    private List<DateTask> dateTasks;

    SimpleDateFormat formatter;

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

        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void listAll_shouldReturnAllTasks() throws Exception {
        Mockito.when(dateTaskService.getAllTask()).thenReturn(dateTasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/dateTask")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskTitle", is("Test Task 1")))
                .andExpect(jsonPath("$[1].taskTitle", is("Test Task 2")))
                .andExpect(jsonPath("$[0].taskStatus", containsString("dias de atraso")))
                .andExpect(jsonPath("$[1].taskStatus", is("Completed")));
    }

    @Test
    public void create_shouldCreateNewTask() throws Exception {
        DateTask v_newTask = new DateTask();
        v_newTask.setTaskTitle("New Task");
        v_newTask.setTaskDesc("New Task Test");
        v_newTask.setTaskStatus("Pending");

        Date v_date = new Date();
        Calendar v_calendar = Calendar.getInstance();
        v_calendar.setTime(v_date);
        v_calendar.add(Calendar.DAY_OF_MONTH, 5);
        v_date = v_calendar.getTime();
        v_newTask.setTaskDateEstimatedConclusion(v_date);

        mockMvc.perform(MockMvcRequestBuilders.post("/dateTask")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("taskTitle", v_newTask.getTaskTitle())
                .param("taskDesc", v_newTask.getTaskDesc())
                .param("taskStatus", v_newTask.getTaskStatus())
                .param("taskDateEstimatedConclusion", formatter.format(v_newTask.getTaskDateEstimatedConclusion())))
                .andExpect(status().isOk());
    }

    @Test
    public void edit_shouldEditTaskStatus() throws Exception {
        DateTask v_updatedTask = new DateTask();
        v_updatedTask.setId(1);
        v_updatedTask.setTaskTitle("Updated Task");
        v_updatedTask.setTaskStatus("In Progress");

        Date v_date = new Date();
        Calendar v_calendar = Calendar.getInstance();
        v_calendar.setTime(v_date);
        v_calendar.add(Calendar.DAY_OF_MONTH, 5);
        v_date = v_calendar.getTime();
        v_updatedTask.setTaskDateEstimatedConclusion(v_date);

        Mockito.when(dateTaskService.editTask(Mockito.any(DateTask.class))).thenReturn("Task updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/dateTask")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("taskId", String.valueOf(v_updatedTask.getId()))
                .param("taskTitle", v_updatedTask.getTaskTitle())
                .param("taskStatus", v_updatedTask.getTaskStatus())
                .param("taskDateEstimatedConclusion", formatter.format(v_updatedTask.getTaskDateEstimatedConclusion())))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated"));
    }
}
