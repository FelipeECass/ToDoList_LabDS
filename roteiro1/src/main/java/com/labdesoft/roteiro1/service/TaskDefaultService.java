package com.labdesoft.roteiro1.service;


import com.labdesoft.roteiro1.entity.ITask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class TaskDefaultService<Entity extends ITask, TypeId> {
    protected JpaRepository<Entity, TypeId> m_taskRepository;

    public List<Entity> getAllTask()
    {
        return m_taskRepository.findAll();
    }
    public String createTask(Entity p_task) throws Exception {
        m_taskRepository.save(p_task);
        return "Task criada com Sucesso";
    }

    public String editTask(Entity p_task)
    {
       Entity v_taskToModify = editTaskParameters(p_task);

       if(v_taskToModify != null)
       {
           m_taskRepository.save(v_taskToModify);
           return "Modificação feita com sucesso.";
       }
       return "Task buscada não existe.";
    }

    protected abstract Entity editTaskParameters(Entity p_task);

}
