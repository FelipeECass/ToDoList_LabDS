package com.labdesoft.roteiro1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dateTask")
@PrimaryKeyJoinColumn(name = "date_Task_Id")
@Schema(description = "Informações da tarefa do tipo de data definida")
public class DateTask extends Task{

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskDateEstimatedConclusion;

}
