package br.com.desafiobackend.picpay.transacoes.validators;

import br.com.desafiobackend.picpay.transacoes.users.TransacaoRequest;
import br.com.desafiobackend.picpay.transacoes.users.User;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SaldoDisponivelValidator implements ConstraintValidator<SaldoDisponivel, TransacaoRequest> {
    private final EntityManager manager;

    public SaldoDisponivelValidator(EntityManager manager) {
        this.manager = manager;
    }

    public boolean isValid(TransacaoRequest request, ConstraintValidatorContext context) {
        User pagador = manager.find(User.class, request.getPagador());
        if (pagador != null) {

            return pagador.getSaldo().doubleValue()>=request.getValor().doubleValue();

        }
        return false;
    }

}
