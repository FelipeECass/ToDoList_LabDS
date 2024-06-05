package com.labdesoft.service;

import com.labdesoft.entity.Task;
import com.labdesoft.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService extends TaskDefaultService<Task, Integer> {

    public TaskService(TaskRepository p_taskRepository)
    {
        m_taskRepository = p_taskRepository;
    }

    @Override
    protected Task editTaskParameters(Task p_task) {
        Optional<Task> v_taskToModify = m_taskRepository.findById(p_task.getId());

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
            return null;
        }
        return v_taskToModify.get();
    }
}
