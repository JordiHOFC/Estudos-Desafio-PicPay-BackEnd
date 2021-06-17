package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.clients.AuthorizationClient;
import br.com.desafiobackend.picpay.transacoes.clients.NotificationClient;
import br.com.desafiobackend.picpay.transacoes.compartilhado.ControleDeTransacaoPragmatico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/transaction")
public class TransacaoController {
    private final EntityManager manager;
    private final ControleDeTransacaoPragmatico controleDeTransacaoPragmatico;
    private final AuthorizationClient authorizationClient;
    private final NotificationClient notificationClient;

    public TransacaoController(EntityManager manager, ControleDeTransacaoPragmatico controleDeTransacaoPragmatico, AuthorizationClient authorizationClient, NotificationClient notificationClient) {
        this.manager = manager;
        this.controleDeTransacaoPragmatico = controleDeTransacaoPragmatico;
        this.authorizationClient = authorizationClient;
        this.notificationClient = notificationClient;
    }

    @PostMapping
    public ResponseEntity<?> realizarTransferencia(@RequestBody @Valid TransacaoRequest request) {

        return controleDeTransacaoPragmatico.executa(() -> {
            User pagador = manager.find(User.class, request.getPagador());
            User beneficiado = manager.find(User.class, request.getBeneficiado());
            try {
                authorizationClient.solicitaAuthorization();
            } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
            }
            Transacao transacao = new Transacao(pagador, beneficiado, request.getValor());
            pagador.sacar(request.getValor(), transacao);
            beneficiado.depositar(request.getValor(), transacao);
            try {
                notificationClient.notificationOk();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }
            URI uri = UriComponentsBuilder.fromUriString("/transaction/{id}").buildAndExpand(transacao.getId()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
        });


    }

}
