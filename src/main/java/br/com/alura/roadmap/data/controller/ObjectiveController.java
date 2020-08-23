package br.com.alura.roadmap.data.controller;

import br.com.alura.roadmap.data.usecases.createObjective.CreateObjectiveRequest;
import br.com.alura.roadmap.data.usecases.createObjective.CreateObjectiveUseCase;
import br.com.alura.roadmap.domain.entities.Objectives;
import br.com.alura.roadmap.infra.database.mongo.ObjectiveRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objective")
public class ObjectiveController {

    @Autowired private ObjectiveRepository repository;
    @Autowired private CreateObjectiveUseCase createObjective;

    @PostMapping("/add")
    public ResponseEntity<Objectives> createObjective(@RequestBody  CreateObjectiveRequest request){
        var response = createObjective.execute(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{objectiveId}")
    public ResponseEntity<Objectives> loadObjectiveById(@PathVariable String objectiveId){
        return ResponseEntity.ok(repository.findById(objectiveId).get());
    }
}