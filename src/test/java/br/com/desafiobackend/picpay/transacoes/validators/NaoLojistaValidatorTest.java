package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.TipoConta;
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

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Transactional
class NaoLojistaValidatorTest {
    @PersistenceContext
    private EntityManager manager;
    private NaoLojistaValidator validator;
    @Mock
    private ConstraintValidatorContext ctx;

    @BeforeEach
    void setUp() {
        this.validator=new NaoLojistaValidator(manager);
    }
    @Test
    @DisplayName("Nao deve ser um lojista")
    public void test1(){
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        manager.persist(pagador);
        final boolean valid = validator.isValid(pagador.getId(), ctx);
        assertTrue(valid);
    }
    @Test
    @DisplayName("Deve ser um lojista")
    public void test2(){
        User pagador = new User("Jordi H.", "104.214.854/0001-21", "jordi2@email.com", "123456", TipoConta.LOJISTA);
        manager.persist(pagador);
        final boolean notValid = validator.isValid(pagador.getId(), ctx);
        assertFalse(notValid);
    }
    @Test
    @DisplayName("Deve ser  quando o id eh nulo lojista")
    public void test3(){
        final boolean notValid = validator.isValid(1L, ctx);
        assertFalse(notValid);
    }
}