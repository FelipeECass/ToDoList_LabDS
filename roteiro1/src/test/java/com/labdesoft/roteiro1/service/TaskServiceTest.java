package com.labdesoft.roteiro1.service;

import com.labdesoft.roteiro1.entity.Task;
import com.labdesoft.roteiro1.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTask_shouldReturnAllTasks() {
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2);
        task2.setTaskTitle("Task 2");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> tasks = taskService.getAllTask();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void createTask_shouldCreateTaskSuccessfully() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setTaskTitle("New Task");

        String result = taskService.createTask(task);

        assertEquals("Task criada com Sucesso", result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void editTask_shouldEditTaskSuccessfully() {
        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTaskTitle("Old Task");
        existingTask.setTaskDesc("Old Description");
        existingTask.setTaskStatus("Pending");

        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTaskTitle("Updated Task");
        updatedTask.setTaskDesc("Updated Description");
        updatedTask.setTaskStatus("Completed");

        when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));

        String result = taskService.editTask(updatedTask);

        assertEquals("Modificação feita com sucesso.", result);
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(existingTask);
        assertEquals("Updated Task", existingTask.getTaskTitle());
        assertEquals("Updated Description", existingTask.getTaskDesc());
        assertEquals("Completed", existingTask.getTaskStatus());
    }

    @Test
    public void editTask_shouldReturnErrorMessageForNonExistingTask() {
        Task updatedTask = new Task();
        updatedTask.setId(1);

        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        String result = taskService.editTask(updatedTask);

        assertEquals("Task buscada não existe.", result);
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void editTask_shouldNotUpdateNullFields() {
        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTaskTitle("Old Task");
        existingTask.setTaskDesc("Old Description");
        existingTask.setTaskStatus("Pending");

        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTaskTitle(null);
        updatedTask.setTaskDesc(null);
        updatedTask.setTaskStatus("Completed");

        when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));

        String result = taskService.editTask(updatedTask);

        assertEquals("Modificação feita com sucesso.", result);
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(existingTask);
        assertEquals("Old Task", existingTask.getTaskTitle());
        assertEquals("Old Description", existingTask.getTaskDesc());
        assertEquals("Completed", existingTask.getTaskStatus());
    }
}
