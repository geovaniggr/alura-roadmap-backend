package br.com.alura.roadmap.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class Task {

    public Task(){
        this.id = ObjectId.get().toHexString();
    }

    private String id;

    private String objective;

    private String goal;

    private String description;

    private String result = "";

    private Double percentOfObjective;

    private Double percentComplete = 0.0;
}