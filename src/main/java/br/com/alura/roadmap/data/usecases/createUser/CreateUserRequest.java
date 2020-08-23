package br.com.alura.roadmap.data.usecases.createUser;

import br.com.alura.roadmap.domain.entities.User;
import br.com.alura.roadmap.domain.entities.enumerators.CaelumTeam;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

public record CreateUserRequest(
   String name,
   String email,
   String password,
   String description,
   CaelumTeam team
) {
    public User toEntity(){
        return new User(name, email, password, description, team);
    }

}
