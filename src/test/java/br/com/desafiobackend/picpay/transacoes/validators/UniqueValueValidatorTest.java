package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UniqueValueValidatorTest {
    private UniqueValueTestCase testCase;
    @Autowired
    private EntityManager manager;

    private UniqueValueValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void  setup(){
        this.context= Mockito.mock(ConstraintValidatorContext.class);
        this.validator=new UniqueValueValidator(manager);
        this.testCase= new UniqueValueTestCase();
    }

    @Test
    @DisplayName("deve ser valido ao inserir um email nunca cadastrado")
    public void test(){
        validator.initialize(testCase);
        boolean valid = validator.isValid("jordi@mail.com", context);
        assertTrue(valid);
    }
    @Test
    @DisplayName("nao deve ser valido ao inserir um email ja cadastrado")
    @Transactional
    public void test1(){
        manager.persist(new User("Jordi Henrique Marques da Silva","385.125.290-08","jordi@email.com","123456"));
        validator.initialize(testCase);
        boolean valid = validator.isValid("jordi@email.com", context);
        assertFalse(valid);
    }


     class UniqueValueTestCase implements UniqueValue{

        @Override
        public String message() {
            return "Valor já cadastrado";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<?> domainClass() {
            return User.class;
        }

        @Override
        public String domainField() {
            return "email";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return UniqueValue.class;
        }
    }

}