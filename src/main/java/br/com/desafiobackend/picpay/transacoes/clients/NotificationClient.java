package br.com.desafiobackend.picpay.transacoes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notify",url = "http://o4d9z.mocklab.io/notify")
public interface NotificationClient {
    @GetMapping
    void notificationOk();
}
