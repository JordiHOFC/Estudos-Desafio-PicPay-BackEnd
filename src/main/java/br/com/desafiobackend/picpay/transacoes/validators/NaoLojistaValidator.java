package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.TipoConta;
import br.com.desafiobackend.picpay.transacoes.users.User;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NaoLojistaValidator implements ConstraintValidator<isNaoLojista,Long> {
    private final EntityManager manager;

    public NaoLojistaValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(isNaoLojista constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        final User user = manager.find(User.class, id);
        if(user==null){
            return false;
        }
        return !user.getTipoConta().equals(TipoConta.LOJISTA);


    }
}
