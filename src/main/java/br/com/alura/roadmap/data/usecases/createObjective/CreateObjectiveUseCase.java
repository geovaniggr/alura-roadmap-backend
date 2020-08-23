package br.com.alura.roadmap.data.usecases.createObjective;

import br.com.alura.roadmap.domain.entities.Objectives;
import br.com.alura.roadmap.infra.database.mongo.ObjectiveRepository;

public class CreateObjectiveUseCase {

    private ObjectiveRepository repository;

    public CreateObjectiveUseCase(ObjectiveRepository repository){
        this.repository = repository;
    }

    public Objectives execute(CreateObjectiveRequest request){
        return repository.save(request.toEntity());
    }
    
}