package br.com.alura.roadmap.data.controller;

import br.com.alura.roadmap.data.usecases.createUser.CreateUserRequest;
import br.com.alura.roadmap.domain.entities.User;
import br.com.alura.roadmap.domain.entities.enumerators.CaelumTeam;
import br.com.alura.roadmap.infra.database.mongo.UserRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MongoOperations mongo;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request){
        var createdUser = repository.save(request.toEntity());

        return ResponseEntity.ok(createdUser);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody CreateUserRequest request) {

        var user = repository.findById(userId);

        if(user.isEmpty())
            return ResponseEntity.badRequest().build();

        copyNonNullProperties(request.toEntity(), user.get());

        var updatedUser = repository.save(user.get());

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUser(@PathVariable String userId){
        var user = repository.findById(userId);

        var path = Paths.get("upload");

        System.out.println(path.toAbsolutePath());

        if(user.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(user.get());
    }

    public void copyNonNullProperties(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullProperties(src).toArray(new String[0]));
    }

    private List<String> getNullProperties(Object src) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(src);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> Objects.isNull(wrappedSource.getPropertyValue(propertyName)))
                .collect(Collectors.toList());

    }

    @GetMapping("/team")
    public ResponseEntity<List<User>> getUsersFromTeam(@RequestParam CaelumTeam name){
        var response = repository.findUserByTeam(name);

        if(response.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(response.get());
    }

    /**
     * TODO: Criar um Service de LocalStorage (?)
     */
    @PostMapping("/teste")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file){
        var uploadDirectory = Paths.get("upload").toAbsolutePath();
        if(Files.notExists(uploadDirectory)) {
            try {
                Files.createDirectories(uploadDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            file.transferTo(uploadDirectory.resolve(file.getOriginalFilename()));
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> addAvatar(String id, Optional<MultipartFile> file){
        var query = new Query().addCriteria(Criteria.where("_id").is(id));

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.ofEntries(
                            entry("Erorr", "É necessario uma imagem")
                    ));
        }

        try {
            var avatar = new Binary(BsonBinarySubType.BINARY, file.get().getBytes());
            var update = new Update().set("avatar", avatar);
            var updated = mongo.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), User.class );
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Teste
     */
    @GetMapping("/avatar/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id){
        System.out.println(id);
        var user = repository.findById(id);

        if(user.isPresent()){
            var image = user.get().getAvatar();

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new ByteArrayResource(image.getData()));
        }

        return null;
    }
}
