package br.com.alura.roadmap.infra.database.mongo;

import br.com.alura.roadmap.domain.entities.Sprint;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.roadmap.domain.entities.Objectives;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjectiveRepository extends MongoRepository<Objectives, String>{

    @Query(value = "{ '_id': ?0 }", fields = "{ '_id': 0, 'sprints': 1 }")
    List<Sprint> findSprintById(String id);

    @Query(value= "{}", fields = "{ '_id': 0, 'sprints': 1}")
    List<Objectives> findAllSprints();

    Optional<List<Objectives>> findByCycleId(ObjectId id);
}