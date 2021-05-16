package br.com.desafiobackend.picpay.transacoes.validators;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {
    private final EntityManager manager;
    private Class<?> domainClass;
    private String field;

    public UniqueValueValidator(EntityManager manager) {
        this.manager = manager;
    }

    public void initialize(UniqueValue constraint) {
        domainClass = constraint.domainClass();
        field = constraint.domainField();

    }

    public boolean isValid(String valor, ConstraintValidatorContext context) {
        String jpql = "select r from " + domainClass.getSimpleName() + " r where " + field + "= :value";
        List<?> resultList = manager.createQuery(jpql)
                .setParameter("value", valor)
                .getResultList();
        return resultList.isEmpty();

    }
}
