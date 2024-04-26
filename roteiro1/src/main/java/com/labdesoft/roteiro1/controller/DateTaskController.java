package com.labdesoft.roteiro1.controller;

import com.labdesoft.roteiro1.entity.DateTask;
import com.labdesoft.roteiro1.service.DateTaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dateTask")
public class DateTaskController {
    private final DateTaskService m_dateTaskService;
    public DateTaskController(DateTaskService p_dateTaskService)
    {
        m_dateTaskService = p_dateTaskService;
    }

    @GetMapping()
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<DateTask>> listAll()
    {
        try
        {
            List<DateTask> v_listOfDateTask = m_dateTaskService.getAllTask();
            if(v_listOfDateTask == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(v_listOfDateTask, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping()
    @Operation(summary = "Cria nova tarefa")
    public ResponseEntity<String> create(@ModelAttribute DateTask dateTask)
    {
        try
        {
            return new ResponseEntity<>(m_dateTaskService.createTask(dateTask),HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PutMapping()
    @Operation(summary = "Muda o status da tarefa")
    public ResponseEntity<String> edit(@ModelAttribute DateTask dateTask)
    {
        try
        {
            return new ResponseEntity<>(m_dateTaskService.editTask(dateTask),HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
