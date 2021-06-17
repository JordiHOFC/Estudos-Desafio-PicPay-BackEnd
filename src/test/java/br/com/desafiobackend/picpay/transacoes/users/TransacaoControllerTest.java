package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.clients.AuthorizationClient;
import br.com.desafiobackend.picpay.transacoes.clients.NotificationClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@Transactional
class TransacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorizationClient authClient;
    @MockBean
    private NotificationClient notifyClient;
    @Autowired
    private UserRepository repository;

//    @Test
//    @DisplayName("Deve realizar a transferencia caso haja saldo")
//    public void test1(){
//        User pagador = new User("Jordi H.","143.498.230-00","jordi@email.com","123456",TipoConta.PESSOAL);
//        repository.saveAndFlush(pagador);
//        pagador.getCarteira().depositar(new BigDecimal("2000"),new Transacao(pagador,pagador,new BigDecimal("2000")));
//        User beneficiado = new User("Jordi H.","61.501.644/0001-58","jordi@email.com","123456",TipoConta.LOJISTA);
//        repository.save(beneficiado);
//
//
//    }

}