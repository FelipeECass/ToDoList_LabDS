package com.labdesoft.roteiro1.controller;

import com.labdesoft.roteiro1.entity.DeadLineTask;
import com.labdesoft.roteiro1.service.DeadLineTaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/deadLineTask")
public class DeadLineTaskController {
    private final DeadLineTaskService m_deadLineTaskService;
    public DeadLineTaskController(DeadLineTaskService p_deadLineTaskService)
    {
        m_deadLineTaskService = p_deadLineTaskService;
    }

    @GetMapping()
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<DeadLineTask>> listAll()
    {
        try
        {
            List<DeadLineTask> v_listOfDeadLineTask = m_deadLineTaskService.getAllTask();
            if(v_listOfDeadLineTask == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            for(DeadLineTask v_dateTaskToChange : v_listOfDeadLineTask)
            {
                String v_status = v_dateTaskToChange.getTaskStatus();
                Integer v_daysInTask = v_dateTaskToChange.getTaskDeadLine();

                if(v_daysInTask < 0)
                {
                    v_dateTaskToChange.setTaskStatus(v_status + ", " + v_daysInTask * -1 + " dias de atraso.");
                }
            }
            return new ResponseEntity<>(v_listOfDeadLineTask, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping()
    @Operation(summary = "Cria nova tarefa")
    public ResponseEntity<String> create(@ModelAttribute DeadLineTask deadLineTask)
    {
        try
        {
            return new ResponseEntity<>(m_deadLineTaskService.createTask(deadLineTask),HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PutMapping()
    @Operation(summary = "Muda o status da tarefa")
    public ResponseEntity<String> edit(@ModelAttribute DeadLineTask deadLineTask)
    {
        try
        {
            return new ResponseEntity<>(m_deadLineTaskService.editTask(deadLineTask),HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
