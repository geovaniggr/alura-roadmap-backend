package br.com.alura.roadmap.core;

import br.com.alura.roadmap.data.usecases.completeSprint.CompleteSprintUseCase;
import br.com.alura.roadmap.data.usecases.createObjective.CreateObjectiveUseCase;
import br.com.alura.roadmap.data.usecases.createSprint.CreateSprintUseCase;
import br.com.alura.roadmap.infra.database.mongo.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class BeansConfiguration {

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Bean
    public CreateObjectiveUseCase createObjectiveUseCase(){
        return new CreateObjectiveUseCase(objectiveRepository);
    }

    @Bean
    public CreateSprintUseCase createSprintUseCase(){
        return new CreateSprintUseCase(mongoOperations);
    }

    @Bean
    public CompleteSprintUseCase CompleteSprintUseCase(){
        return new CompleteSprintUseCase(mongoOperations);
    }

}
