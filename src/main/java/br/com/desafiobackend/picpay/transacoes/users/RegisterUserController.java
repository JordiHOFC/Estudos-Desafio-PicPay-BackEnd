package br.com.desafiobackend.picpay.transacoes.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class RegisterUserController {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterUserController(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest request){
        User user = request.toUser(bCryptPasswordEncoder);
       repository.save(user);
        URI uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}

