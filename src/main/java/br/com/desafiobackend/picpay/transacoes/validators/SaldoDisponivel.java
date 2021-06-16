package br.com.desafiobackend.picpay.transacoes.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE,TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SaldoDisponivelValidator.class)
public @interface SaldoDisponivel {
    String message() default "saldo insuficiente para operacao.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
