package br.com.alura.roadmap.domain.entities;

import java.util.List;

import br.com.alura.roadmap.domain.entities.enumerators.DifficultyEnum;
import br.com.alura.roadmap.domain.entities.enumerators.Priority;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(name = "id_sprint", def = "{ '_id': 1, 'sprints.id': 1 }")
public class Objectives {

    @Id
    private String id;

    private double percent = 0;

    private String cycleId;

    private String description;

    private String supportColaborator;

    private DifficultyEnum difficulty;

    private Priority priority;

    private List<PartialSprint> sprints;

    private List<String> goals;

    public Objectives(String cycleId, String description, String supportColaborator, DifficultyEnum difficulty, Priority priority, List<String> goals) {
        this.cycleId = cycleId;
        this.description = description;
        this.supportColaborator = supportColaborator;
        this.difficulty = difficulty;
        this.priority = priority;
        this.goals = goals;
    }
}