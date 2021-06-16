package br.com.desafiobackend.picpay.transacoes.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransacaoController {
    private final EntityManager manager;

    public TransacaoController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> realizarTransferencia(@RequestBody @Valid TransacaoRequest request) {
        User pagador = manager.find(User.class, request.getPagador());
        User beneficiado = manager.find(User.class, request.getBeneficiado());
        pagador.sacar(request.getValor(),new Transacao(pagador,beneficiado, pagador.getCarteira(), request.getValor()));
        beneficiado.depositar(request.getValor(),new Transacao(pagador,beneficiado, beneficiado.getCarteira(), request.getValor()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
