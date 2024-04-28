package com.labdesoft.roteiro1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deadLineTask")
@PrimaryKeyJoinColumn(name = "dead_Line_Task_Id")
@Schema(description = "Informações da tarefa do tipo de data definida")
public class DeadLineTask extends Task{

    @Column(nullable = false)
    private Integer taskDeadLine;

}
