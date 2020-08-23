package br.com.alura.roadmap.data.usecases.createObjective;

import br.com.alura.roadmap.domain.entities.Objectives;
import br.com.alura.roadmap.domain.entities.enumerators.DifficultyEnum;
import br.com.alura.roadmap.domain.entities.enumerators.Priority;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record CreateObjectiveRequest(
        @NotNull @NotEmpty String cycleId,
        @NotNull @NotEmpty String description,
        @NotEmpty @NotEmpty String supportColaborator,
        @NotNull @NotEmpty Priority priority,
        @NotNull @NotEmpty DifficultyEnum difficulty,
        @NotNull List<String> goals
) {
    public Objectives toEntity(){
        return new Objectives(
                cycleId,
                description,
                supportColaborator,
                difficulty,
                priority,
                goals
        );
    }
    
}