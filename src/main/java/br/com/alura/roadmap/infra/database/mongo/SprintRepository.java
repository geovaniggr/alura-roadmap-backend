package br.com.alura.roadmap.infra.database.mongo;

import br.com.alura.roadmap.domain.entities.Sprint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SprintRepository extends MongoRepository<Sprint, String> {
}
