package com.labdesoft.roteiro1.service;

import com.labdesoft.roteiro1.entity.Task;
import com.labdesoft.roteiro1.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository m_taskRepository;

    public List<Task> getAllTask()
    {
        return m_taskRepository.findAll();
    }
    public String createTask(Task p_task)
    {
        m_taskRepository.save(p_task);
        return "Task criada com Sucesso";
    }

    public String editTask(Task p_task)
    {
        Optional<Task> v_taskToModify = m_taskRepository.findById(p_task.getTaskId());

        if(v_taskToModify.isPresent())
        {
            if(p_task.getTaskTitle() != null)
                v_taskToModify.get().setTaskTitle(p_task.getTaskTitle());
            if(p_task.getTaskDesc() != null)
                v_taskToModify.get().setTaskDesc(p_task.getTaskDesc());
            if(p_task.getTaskStatus() != null)
                v_taskToModify.get().setTaskStatus(p_task.getTaskStatus());
        }else
        {
            return "Task buscada não existe.";
        }
        m_taskRepository.save(v_taskToModify.get());
        return "Modificação feita com sucesso.";
    }
}
