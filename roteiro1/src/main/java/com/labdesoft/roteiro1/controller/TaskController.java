package com.labdesoft.roteiro1.controller;

import com.labdesoft.roteiro1.entity.Task;
import com.labdesoft.roteiro1.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService m_taskService;

    @GetMapping("/listAll")
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<Task>> listAll()
    {
        try
        {
            List<Task> v_listOfTask = m_taskService.getAllTask();
            if(v_listOfTask == null)
                return new ResponseEntity<List<Task>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Task>>(m_taskService.getAllTask(), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Cria nova tarefa")
    public ResponseEntity<String> create(@ModelAttribute Task task)
    {
        try
        {
            return new ResponseEntity<String>(m_taskService.createTask(task),HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PutMapping(value = {"/setTaskAsComplete","/setTaskAsPending"})
    @Operation(summary = "Muda o status da tarefa")
    public ResponseEntity<String> edit(@ModelAttribute Task task)
    {
        try
        {
            return new ResponseEntity<String>(m_taskService.editTask(task),HttpStatus.OK);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
