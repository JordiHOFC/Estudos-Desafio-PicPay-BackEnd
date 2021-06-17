package br.com.desafiobackend.picpay.transacoes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authTransaction",url = "${picpay.api.authorization}")
public interface AuthorizationClient {
    @GetMapping
    ResponseEntity<?> solicitaAuthorization();
}
