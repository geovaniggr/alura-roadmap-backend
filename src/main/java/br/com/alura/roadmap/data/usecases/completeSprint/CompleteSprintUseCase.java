package br.com.alura.roadmap.data.usecases.completeSprint;

import br.com.alura.roadmap.domain.entities.Objectives;
import br.com.alura.roadmap.domain.entities.Sprint;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompleteSprintUseCase {
    private MongoOperations mongo;

    public CompleteSprintUseCase(MongoOperations mongo){
        this.mongo = mongo;
    }

    public Optional<Objectives> execute(String objectiveId, CompletedSprintRequest request){

        var updateObjective = updateObjective(objectiveId, request.sprintId(), request.tasks());
        
        if(updateObjective.isPresent()){
            updateSprintTasks(request.sprintId(), request.tasks());
        }

        return updateObjective;
    }

    private Optional<Objectives> updateObjective(String objectiveId, String sprintId, List<CompletedTask> tasks){
        var completedPercent = calculateSprintCompletedPercent(tasks);

        var query = new Query()
                .addCriteria(Criteria
                        .where("_id").is(objectiveId)
                        .and("sprints").elemMatch(Criteria
                                .where("sprintId").is(sprintId)
                                .and("completed").is(false)));

        var update = new Update()
                .inc("percent", completedPercent)
                .set("sprints.$.completed", true);

        return Optional.ofNullable(mongo.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                Objectives.class));
    }

    private void updateSprintTasks(String sprintId, List<CompletedTask> tasks){
        var bulkOperations = mongo.bulkOps(BulkOperations.BulkMode.UNORDERED, Sprint.class);
        var updates = new ArrayList<Pair<Query, Update>>();

        tasks.forEach( task -> {
            var taskQuery = new Query()
                        .addCriteria(Criteria
                                .where("tasks").elemMatch(Criteria.where("_id").is(task.id()))
                                .and("_id").is(sprintId));

            var taskUpdate = new Update()
                    .inc("tasks.$.percentComplete", task.percentCompleted())
                    .set("tasks.$.result", task.description());

            updates.add(Pair.of(taskQuery, taskUpdate));
        });

        bulkOperations.updateMulti(updates).execute();
    }

    private double calculateSprintCompletedPercent(List<CompletedTask> tasks){
        return tasks
                .stream()
                .mapToDouble(CompletedTask::percentCompleted)
                .sum();
    }
}
