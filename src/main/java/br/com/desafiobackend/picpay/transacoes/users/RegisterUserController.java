package br.com.desafiobackend.picpay.transacoes.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class RegisterUserController {
    private final EntityManager manager;

    public RegisterUserController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest request){
        User user = request.toUser();
        manager.persist(user);
        URI uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
