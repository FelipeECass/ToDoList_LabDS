package com.labdesoft.roteiro1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
@Inheritance(strategy = InheritanceType.JOINED)
@Schema(description = "Informações da tarefa")
public class Task implements ITask {
    public enum Status {
        Pendente, Finalizada
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("taskId")
    private Integer Id;
    @Column(nullable = false)
    private String taskTitle;
    @Column(nullable = true)
    private String taskDesc;
    @Column(nullable = false)
    private Status taskStatus;
}
