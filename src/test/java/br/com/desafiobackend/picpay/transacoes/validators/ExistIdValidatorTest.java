package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.TipoConta;
import br.com.desafiobackend.picpay.transacoes.users.User;
import br.com.desafiobackend.picpay.transacoes.users.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class ExistIdValidatorTest {
    @PersistenceContext
    private EntityManager manager;
    private ExistIdValidator validator ;
    @Mock
    private ConstraintValidatorContext ctx;

    @BeforeEach
    public void setUp(){
        this.validator= new ExistIdValidator(manager);
    }

    @Test
    @DisplayName("Deve existir um id para este usuario")

    public void test1() {
        User pagador = new User("Jordi H.", "143.498.230-00", "jordi@email.com", "123456", TipoConta.PESSOAL);
        manager.persist(pagador);
        boolean valid = validator.isValid(pagador.getId(),ctx);
        assertTrue(valid);
    }
    @Test
    @DisplayName("nao deve existir um id para este usuario")
    public void test2() {
        boolean valid = validator.isValid(1L,ctx);
        assertFalse(valid);
    }
}