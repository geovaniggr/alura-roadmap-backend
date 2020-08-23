package br.com.alura.roadmap.domain.entities;

import br.com.alura.roadmap.domain.entities.enumerators.CaelumTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private String id;

    private String email;

    private String password;

    private Binary avatar;

    private String name;

    private String description;

    private CaelumTeam team;

    public User(String name, String email, String password, String description, CaelumTeam team) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.team = team;
    }
}
