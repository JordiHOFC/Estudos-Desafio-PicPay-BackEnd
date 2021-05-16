package br.com.desafiobackend.picpay.transacoes.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RestController
@RequestMapping("/shoppes")
public class RegisterShoppeController {
    private final EntityManager manager;

    public RegisterShoppeController(EntityManager manager) {
        this.manager = manager;
    }


}
