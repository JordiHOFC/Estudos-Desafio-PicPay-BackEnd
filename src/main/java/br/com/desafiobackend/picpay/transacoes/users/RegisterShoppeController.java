package br.com.desafiobackend.picpay.transacoes.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/shoppes")
public class RegisterShoppeController {
    private final EntityManager manager;

    public RegisterShoppeController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerShoppe(@RequestBody @Valid ShoppeRequest request) {
        Shoppe shoppe = request.toShoppe();
        manager.persist(shoppe);

        URI location = UriComponentsBuilder.fromUriString("/shoppes/{id}").buildAndExpand(shoppe.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}
