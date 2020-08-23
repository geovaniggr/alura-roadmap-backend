package br.com.alura.roadmap.data.usecases.createSprint;

import br.com.alura.roadmap.domain.entities.Objectives;
import br.com.alura.roadmap.domain.entities.PartialSprint;
import br.com.alura.roadmap.domain.entities.Sprint;
import br.com.alura.roadmap.domain.entities.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.stream.Collectors;

public class CreateSprintUseCase {

    private MongoOperations mongo;

    public CreateSprintUseCase(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public Objectives execute(String objectiveId, CreateSprintRequest sprints){

        var createdSprint = mongo.save(sprints.toEntity());
        var updatedObjective = addPartialTaskToObjective(objectiveId, createdSprint);

        return updatedObjective;
    }

    public Objectives addPartialTaskToObjective(String objectiveId, Sprint sprints){

        var query = new Query().addCriteria(Criteria.where("_id").is(objectiveId));
        var partialSprints = new PartialSprint(sprints);
        var update = new Update().addToSet("sprints", partialSprints);

        return mongo.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                Objectives.class);
    }

}
