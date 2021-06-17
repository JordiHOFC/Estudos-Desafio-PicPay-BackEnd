package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.clients.AuthorizationClient;
import br.com.desafiobackend.picpay.transacoes.clients.NotificationClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@Transactional
class TransacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorizationClient authClient;


    @MockBean(NotificationClient.class)
    private NotificationClient notifyClient;


    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Deve realizar a transferencia caso haja saldo")
    public void test1() throws Exception {
        //preparando o ambiente
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        repository.saveAndFlush(pagador);
        pagador.getCarteira().depositar(new BigDecimal("2000"), new Transacao(pagador, pagador, new BigDecimal("2000")));
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        repository.save(beneficiado);

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), pagador.getId(), beneficiado.getId());
        String jsonRequest = mapper.writeValueAsString(transacaoRequest);
        //realizando chamadas

        Mockito.when(authClient.solicitaAuthorization()).thenReturn(ResponseEntity.ok().build());
        Mockito.when(notifyClient.notificationOk()).thenReturn(ResponseEntity.ok().build());
        //chamada no endpoint
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/transaction").build().toUri();
        mockMvc.perform(
                post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        redirectedUrlPattern("/transaction/*")
                );


    }
    @Test
    @DisplayName("Nao deve realizar a transferencia caso nao aconteça a notificacao")
    public void test2() throws Exception {
        //preparando o ambiente
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        repository.saveAndFlush(pagador);
        pagador.getCarteira().depositar(new BigDecimal("2000"), new Transacao(pagador, pagador, new BigDecimal("2000")));
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        repository.save(beneficiado);

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), pagador.getId(), beneficiado.getId());
        String jsonRequest = mapper.writeValueAsString(transacaoRequest);
        //realizando chamadas

        Mockito.when(authClient.solicitaAuthorization()).thenReturn(ResponseEntity.badRequest().build());
        Mockito.when(notifyClient.notificationOk()).thenThrow(FeignException.FeignClientException.class);
        //chamada no endpoint
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/transaction").build().toUri();
        mockMvc.perform(
                post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(
                        status().is(412)
                );



    }
    @Test
    @DisplayName("Nao deve realizar a transferencia caso o  pagamento nao seja authorizado")
    public void test3() throws Exception {
        //preparando o ambiente
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        repository.saveAndFlush(pagador);
        pagador.getCarteira().depositar(new BigDecimal("2000"), new Transacao(pagador, pagador, new BigDecimal("2000")));
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        repository.save(beneficiado);

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), pagador.getId(), beneficiado.getId());
        String jsonRequest = mapper.writeValueAsString(transacaoRequest);
        //realizando chamadas

        Mockito.when(authClient.solicitaAuthorization()).thenThrow(FeignException.FeignClientException.class);

        //chamada no endpoint
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/transaction").build().toUri();
        mockMvc.perform(
                post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(
                        status().is(502)
                );



    }
    @Test
    @DisplayName("Nao deve realizar a transferencia caso o  saldo seja insuficiente  nao seja authorizado")
    public void test4() throws Exception {
        //preparando o ambiente
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        repository.saveAndFlush(pagador);
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        repository.save(beneficiado);

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), pagador.getId(), beneficiado.getId());
        String jsonRequest = mapper.writeValueAsString(transacaoRequest);
        //realizando chamadas

        Mockito.when(authClient.solicitaAuthorization()).thenReturn(ResponseEntity.ok().build());
        Mockito.when(notifyClient.notificationOk()).thenReturn(ResponseEntity.ok().build());

        //chamada no endpoint
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/transaction").build().toUri();
        mockMvc.perform(
                post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(
                        status().isBadRequest()
                );



    }


}