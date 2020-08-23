package br.com.alura.roadmap.domain.entities;import br.com.alura.roadmap.domain.entities.Sprint;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PartialSprint {
    private LocalDate initialDate;
    private String sprintId;
    private Boolean completed = false;

    public PartialSprint(Sprint sprint){
        this.initialDate = sprint.getInitialDate();
        this.sprintId = sprint.getId();
    }
}
