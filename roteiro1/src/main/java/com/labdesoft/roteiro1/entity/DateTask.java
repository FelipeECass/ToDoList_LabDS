package com.labdesoft.roteiro1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dateTask")
@Schema(description = "Informações da tarefa do tipo de data definida")
public class DateTask extends Task{

    @Column(nullable = false)
    private Date taskDateEstimatedConclusion;

    public DateTask(String p_title, String p_desc, Status p_status, Date p_taskDateEstimatedConclusion) {
        super(p_title, p_desc, p_status);
        this.taskDateEstimatedConclusion = p_taskDateEstimatedConclusion;
    }

}
