package br.com.alura.roadmap.domain.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@NoArgsConstructor
@CompoundIndex(name = "id_task", def = "{ '_id': 1, 'task': 1}")
@Document
public class Sprint {

    private String id;

    private String userId;

    @DateTimeFormat(pattern = "dd/MM/yyyy") @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate initialDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy") @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    private List<Task> tasks;

    public Sprint(LocalDate initialDate, LocalDate endDate, List<Task> tasks) {
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }
}