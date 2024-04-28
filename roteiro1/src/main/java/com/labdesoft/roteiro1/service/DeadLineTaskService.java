package com.labdesoft.roteiro1.service;

import com.labdesoft.roteiro1.entity.DeadLineTask;

import com.labdesoft.roteiro1.repository.DeadLineTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeadLineTaskService extends TaskDefaultService<DeadLineTask, Integer>{
    public DeadLineTaskService(DeadLineTaskRepository p_datetaskRepository)
    {
        m_taskRepository = p_datetaskRepository;
    }

    @Override
    protected DeadLineTask editTaskParameters(DeadLineTask p_task) {
        Optional<DeadLineTask> v_deadLineTaskToModify = m_taskRepository.findById(p_task.getId());

        if(v_deadLineTaskToModify.isPresent())
        {
            if(p_task.getTaskTitle() != null)
                v_deadLineTaskToModify.get().setTaskTitle(p_task.getTaskTitle());
            if(p_task.getTaskDesc() != null)
                v_deadLineTaskToModify.get().setTaskDesc(p_task.getTaskDesc());
            if(p_task.getTaskStatus() != null)
                v_deadLineTaskToModify.get().setTaskStatus(p_task.getTaskStatus());
            if(p_task.getTaskDeadLine() != null)
                v_deadLineTaskToModify.get().setTaskDeadLine(p_task.getTaskDeadLine());
        }else
        {
            return null;
        }
        return v_deadLineTaskToModify.get();
    }

}
