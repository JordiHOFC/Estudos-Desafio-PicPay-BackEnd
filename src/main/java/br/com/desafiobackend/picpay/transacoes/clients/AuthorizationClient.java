package br.com.desafiobackend.picpay.transacoes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authTransaction",url = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6")
public interface AuthorizationClient {
    @GetMapping
    void solicitaAuthorization();
}
