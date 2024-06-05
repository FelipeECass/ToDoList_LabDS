package com.labdesoft.service;

import com.labdesoft.entity.DateTask;
import com.labdesoft.repository.DateTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class DateTaskService extends TaskDefaultService<DateTask, Integer>{
    public DateTaskService(DateTaskRepository p_datetaskRepository)
    {
        m_taskRepository = p_datetaskRepository;
    }

    @Override
    public String createTask(DateTask p_task) throws Exception {
        if(p_task.getTaskDateEstimatedConclusion().before(new Date()))
            throw new Exception("The date is earlier than the current date.");
        m_taskRepository.save(p_task);
        return "Task criada com Sucesso";
    }
    @Override
    protected DateTask editTaskParameters(DateTask p_task) {
        Optional<DateTask> v_dateTaskToModify = m_taskRepository.findById(p_task.getId());

        if(v_dateTaskToModify.isPresent())
        {
            if(p_task.getTaskTitle() != null)
                v_dateTaskToModify.get().setTaskTitle(p_task.getTaskTitle());
            if(p_task.getTaskDesc() != null)
                v_dateTaskToModify.get().setTaskDesc(p_task.getTaskDesc());
            if(p_task.getTaskStatus() != null)
                v_dateTaskToModify.get().setTaskStatus(p_task.getTaskStatus());
            if(p_task.getTaskDateEstimatedConclusion() != null && p_task.getTaskDateEstimatedConclusion().after(new Date()))
                v_dateTaskToModify.get().setTaskDateEstimatedConclusion(p_task.getTaskDateEstimatedConclusion());
        }else
        {
            return null;
        }
        return v_dateTaskToModify.get();
    }
    
}
