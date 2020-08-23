package br.com.alura.roadmap.infra.database.mongo;

import br.com.alura.roadmap.domain.entities.Cycle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleRepository extends MongoRepository<Cycle, String> {
}
