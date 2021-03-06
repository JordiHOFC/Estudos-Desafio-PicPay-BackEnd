package br.com.desafiobackend.picpay.transacoes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notify",url = "${picpay.api.notification}")
public interface NotificationClient {
    @GetMapping
    ResponseEntity<?> notificationOk();
}
