package br.com.alura.roadmap.infra.database.mongo;

import br.com.alura.roadmap.domain.entities.User;
import br.com.alura.roadmap.domain.entities.enumerators.CaelumTeam;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<List<User>> findUserByTeam(CaelumTeam team);
}
