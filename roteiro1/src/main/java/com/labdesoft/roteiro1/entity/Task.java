package com.labdesoft.roteiro1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "task")
@Schema(description = "Informações da tarefa")
public class Task implements ITask {
    public enum Status {
        Pendente, Finalizada;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    @Column(nullable = false)
    private String taskTitle;
    @Column(nullable = true)
    private String taskDesc;
    @Column(nullable = false)
    private Status taskStatus;
    public Task(String p_title, String p_desc, Status p_status)
    {
        taskTitle = p_title;
        taskDesc = p_desc;
        taskStatus = p_status;
    }
    public Task(String p_title, Status p_status)
    {
        taskTitle = p_title;
        taskStatus = p_status;
    }

    public Task(String p_title)
    {
        taskTitle = p_title;
        taskStatus = Status.Pendente;
    }
    @Override
    public Integer getId() {
        return taskId;
    }
    @Override
    public String toString() {
        return "Task [id = " + taskId + ", title=" + taskTitle + ", description=" + taskDesc + ", status=" + taskStatus + "]";
    }

}
