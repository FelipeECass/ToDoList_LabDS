package com.labdesoft.roteiro1.service;

import com.labdesoft.roteiro1.entity.DeadLineTask;
import com.labdesoft.roteiro1.repository.DeadLineTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeadLineTaskServiceTest {

    @Mock
    private DeadLineTaskRepository deadLineTaskRepository;

    @InjectMocks
    private DeadLineTaskService deadLineTaskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void editTaskParameters_shouldUpdateTaskSuccessfully() {
        DeadLineTask existingTask = new DeadLineTask();
        existingTask.setId(1);
        existingTask.setTaskTitle("Old Title");
        existingTask.setTaskDesc("Old Description");
        existingTask.setTaskStatus("Pending");
        existingTask.setTaskDeadLine(5); // 5 days remaining

        DeadLineTask updatedTask = new DeadLineTask();
        updatedTask.setId(1);
        updatedTask.setTaskTitle("New Title");
        updatedTask.setTaskDesc("New Description");
        updatedTask.setTaskStatus("Completed");
        updatedTask.setTaskDeadLine(3); // 3 days remaining

        when(deadLineTaskRepository.findById(1)).thenReturn(Optional.of(existingTask));

        DeadLineTask result = deadLineTaskService.editTaskParameters(updatedTask);

        assertNotNull(result);
        assertEquals("New Title", result.getTaskTitle());
        assertEquals("New Description", result.getTaskDesc());
        assertEquals("Completed", result.getTaskStatus());
        assertEquals(3, result.getTaskDeadLine());

        verify(deadLineTaskRepository, times(1)).findById(1);
    }

    @Test
    public void editTaskParameters_shouldReturnNullForNonExistingTask() {
        DeadLineTask updatedTask = new DeadLineTask();
        updatedTask.setId(1);

        when(deadLineTaskRepository.findById(1)).thenReturn(Optional.empty());

        DeadLineTask result = deadLineTaskService.editTaskParameters(updatedTask);

        assertNull(result);
        verify(deadLineTaskRepository, times(1)).findById(1);
    }

    @Test
    public void editTaskParameters_shouldNotUpdateNullFields() {
        DeadLineTask existingTask = new DeadLineTask();
        existingTask.setId(1);
        existingTask.setTaskTitle("Old Title");
        existingTask.setTaskDesc("Old Description");
        existingTask.setTaskStatus("Pending");
        existingTask.setTaskDeadLine(5); // 5 days remaining

        DeadLineTask updatedTask = new DeadLineTask();
        updatedTask.setId(1);
        updatedTask.setTaskTitle(null);
        updatedTask.setTaskDesc(null);
        updatedTask.setTaskStatus("Completed");
        updatedTask.setTaskDeadLine(null);

        when(deadLineTaskRepository.findById(1)).thenReturn(Optional.of(existingTask));

        DeadLineTask result = deadLineTaskService.editTaskParameters(updatedTask);

        assertNotNull(result);
        assertEquals("Old Title", result.getTaskTitle());
        assertEquals("Old Description", result.getTaskDesc());
        assertEquals("Completed", result.getTaskStatus());
        assertEquals(5, result.getTaskDeadLine());

        verify(deadLineTaskRepository, times(1)).findById(1);
    }
}
