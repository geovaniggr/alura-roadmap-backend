package br.com.alura.roadmap.data.usecases.createSprint;

import br.com.alura.roadmap.domain.entities.Sprint;
import br.com.alura.roadmap.domain.entities.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record CreateSprintRequest(
    String objectiveId,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("endDate")
    LocalDate endDate,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("initialDate")
    LocalDate initialDate,

    List<Task> tasks
){
    public Sprint toEntity(){
        return new Sprint(
                initialDate,
                endDate,
                tasks
        );
    }
}