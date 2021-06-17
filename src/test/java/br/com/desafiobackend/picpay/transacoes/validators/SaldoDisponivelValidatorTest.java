package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.TipoConta;
import br.com.desafiobackend.picpay.transacoes.users.Transacao;
import br.com.desafiobackend.picpay.transacoes.users.TransacaoRequest;
import br.com.desafiobackend.picpay.transacoes.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Transactional
class SaldoDisponivelValidatorTest {
    @PersistenceContext
    private EntityManager manager;
    @Mock
    private ConstraintValidatorContext ctx;
    private SaldoDisponivelValidator validator;

    @BeforeEach
    void setUp() {
        this.validator=new SaldoDisponivelValidator(manager);
    }

    @Test
    @DisplayName("O saldo deve ser disponivel")
    public void test1(){
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        manager.persist(pagador);
        pagador.getCarteira().depositar(new BigDecimal("2000"), new Transacao(pagador, pagador, new BigDecimal("2000")));
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        manager.persist(beneficiado);
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), pagador.getId(), beneficiado.getId());

        final boolean valid = validator.isValid(transacaoRequest, ctx);
        assertTrue(valid);
    }
    @Test
    @DisplayName("O saldo nao deve ser disponivel")
    public void test2(){
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        manager.persist(pagador);
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        manager.persist(beneficiado);
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), pagador.getId(), beneficiado.getId());

        final boolean notValid = validator.isValid(transacaoRequest, ctx);
        assertFalse(notValid);
    }
    @Test
    @DisplayName("O saldo nao deve ser disponivel caso nao exista o pagador")
    public void test3(){
        User beneficiado = new User("Jordi H.", "61.501.644/0001-58", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        manager.persist(beneficiado);
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("200.00"), 2L, beneficiado.getId());
        final boolean notValid = validator.isValid(transacaoRequest, ctx);
        assertFalse(notValid);
    }
}