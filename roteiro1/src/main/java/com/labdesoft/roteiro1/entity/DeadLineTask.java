package com.labdesoft.roteiro1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "deadLineTask")
@Schema(description = "Informações da tarefa do tipo de data definida")
public class DeadLineTask extends Task{

    @Column(nullable = false)
    private String taskDeadLine;

    public DeadLineTask(String p_title, String p_desc, Status p_status, String p_taskDateEstimatedConclusion) {
        super(p_title, p_desc, p_status);
        this.taskDeadLine = p_taskDateEstimatedConclusion;
    }

}
