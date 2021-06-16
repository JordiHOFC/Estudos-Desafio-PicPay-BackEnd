package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.User;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistIdValidator implements ConstraintValidator<ExistId, Long> {
    private final EntityManager manager;

    public ExistIdValidator(EntityManager manager) {
        this.manager = manager;
    }

    public boolean isValid(Long id, ConstraintValidatorContext context) {
        final User user = manager.find(User.class, id);
        return user!=null;
    }
}
