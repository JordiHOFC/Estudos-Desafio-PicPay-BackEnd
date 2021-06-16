package br.com.desafiobackend.picpay.transacoes.validators;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@ConstraintComposition(CompositionType.OR)
@CPF
@ReportAsSingleViolation
@CNPJ
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface DocumentoValido {
    String message() default "o campo deve ser formatado como cpf ou cnpj";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
