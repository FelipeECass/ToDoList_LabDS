package com.labdesoft.roteiro1.service;

import com.labdesoft.roteiro1.entity.DateTask;
import com.labdesoft.roteiro1.repository.DateTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DateTaskServiceTest {

    @Mock
    private DateTaskRepository dateTaskRepository;

    @InjectMocks
    private DateTaskService dateTaskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTask_shouldCreateTaskSuccessfully() throws Exception {
        DateTask task = new DateTask();
        task.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() + 86400000)); // 1 day in the future

        String result = dateTaskService.createTask(task);

        assertEquals("Task criada com Sucesso", result);
        verify(dateTaskRepository, times(1)).save(task);
    }

    @Test
    public void createTask_shouldThrowExceptionForPastDate() {
        DateTask task = new DateTask();
        task.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() - 86400000)); // 1 day in the past

        Exception exception = assertThrows(Exception.class, () -> {
            dateTaskService.createTask(task);
        });

        assertEquals("The date is earlier than the current date.", exception.getMessage());
        verify(dateTaskRepository, never()).save(task);
    }

    @Test
    public void editTaskParameters_shouldUpdateTaskSuccessfully() {
        DateTask existingTask = new DateTask();
        existingTask.setId(1);
        existingTask.setTaskTitle("Old Title");
        existingTask.setTaskDesc("Old Description");
        existingTask.setTaskStatus("Pending");
        existingTask.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() + 86400000)); // 1 day in the future

        DateTask updatedTask = new DateTask();
        updatedTask.setId(1);
        updatedTask.setTaskTitle("New Title");
        updatedTask.setTaskDesc("New Description");
        updatedTask.setTaskStatus("Completed");
        updatedTask.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() + 172800000)); // 2 days in the future

        when(dateTaskRepository.findById(1)).thenReturn(Optional.of(existingTask));

        DateTask result = dateTaskService.editTaskParameters(updatedTask);

        assertNotNull(result);
        assertEquals("New Title", result.getTaskTitle());
        assertEquals("New Description", result.getTaskDesc());
        assertEquals("Completed", result.getTaskStatus());
        assertEquals(updatedTask.getTaskDateEstimatedConclusion(), result.getTaskDateEstimatedConclusion());

        verify(dateTaskRepository, times(1)).findById(1);
    }

    @Test
    public void editTaskParameters_shouldReturnNullForNonExistingTask() {
        DateTask updatedTask = new DateTask();
        updatedTask.setId(1);

        when(dateTaskRepository.findById(1)).thenReturn(Optional.empty());

        DateTask result = dateTaskService.editTaskParameters(updatedTask);

        assertNull(result);
        verify(dateTaskRepository, times(1)).findById(1);
    }

    @Test
    public void editTaskParameters_shouldNotUpdateDateIfBeforeCurrentDate() {
        DateTask existingTask = new DateTask();
        existingTask.setId(1);
        existingTask.setTaskTitle("Old Title");
        existingTask.setTaskDesc("Old Description");
        existingTask.setTaskStatus("Pending");
        existingTask.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() + 86400000)); // 1 day in the future

        DateTask updatedTask = new DateTask();
        updatedTask.setId(1);
        updatedTask.setTaskDateEstimatedConclusion(new Date(System.currentTimeMillis() - 86400000)); // 1 day in the past

        when(dateTaskRepository.findById(1)).thenReturn(Optional.of(existingTask));

        DateTask result = dateTaskService.editTaskParameters(updatedTask);

        assertNotNull(result);
        assertEquals(existingTask.getTaskDateEstimatedConclusion(), result.getTaskDateEstimatedConclusion());

        verify(dateTaskRepository, times(1)).findById(1);
    }
}
