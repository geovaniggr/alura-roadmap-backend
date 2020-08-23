package br.com.alura.roadmap.data.controller;

import br.com.alura.roadmap.data.usecases.createCycle.CreateCycleRequest;
import br.com.alura.roadmap.domain.entities.Cycle;
import br.com.alura.roadmap.infra.database.mongo.CycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cycle")
public class CycleController {

    @Autowired
    CycleRepository repository;

    @PostMapping
    public ResponseEntity<Cycle> createCycle(@RequestBody @Valid CreateCycleRequest request){
        var createdCycle = repository.save(request.toEntity());
        return ResponseEntity.ok(createdCycle);
    }
}
