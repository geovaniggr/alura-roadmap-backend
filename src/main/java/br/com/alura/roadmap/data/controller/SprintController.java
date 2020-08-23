package br.com.alura.roadmap.data.controller;

import br.com.alura.roadmap.data.usecases.completeSprint.CompleteSprintUseCase;
import br.com.alura.roadmap.data.usecases.completeSprint.CompletedSprintRequest;
import br.com.alura.roadmap.data.usecases.createSprint.CreateSprintRequest;
import br.com.alura.roadmap.data.usecases.createSprint.CreateSprintUseCase;
import br.com.alura.roadmap.domain.entities.Objectives;
import br.com.alura.roadmap.infra.database.mongo.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Map.entry;

@RestController
@RequestMapping("/objective")
public class SprintController {

    @Autowired
    private CreateSprintUseCase createSprint;

    @Autowired
    private CompleteSprintUseCase completeSprint;

    @Autowired
    private SprintRepository repository;

    @PostMapping("/{objectiveId}/sprint/add")
    public ResponseEntity<Objectives> addSprintToObjective(@PathVariable String objectiveId, @RequestBody CreateSprintRequest request){
        var response = createSprint.execute(objectiveId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{objectiveId}/sprint/complete")
    public ResponseEntity<?> completeSprint(@PathVariable String objectiveId, @RequestBody CompletedSprintRequest request){
        var response = completeSprint.execute(objectiveId, request);

        if(response.isPresent())
            return ResponseEntity.ok(response.get());

        return ResponseEntity.badRequest().body(Map.ofEntries(
                entry("Error", "Não existente ou já criado")
        ));
    }

    @GetMapping("/sprint/{id}")
    public ResponseEntity<?> loadSprintById(@PathVariable String id){
        return ResponseEntity.ok(repository.findById(id));
    }
}
