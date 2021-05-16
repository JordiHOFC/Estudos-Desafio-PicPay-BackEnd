package br.com.desafiobackend.picpay.transacoes.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {
    String message() default "Valor já cadastrado na base da dados";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> domainClass();
    String domainField();
}
