package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.validators.UniqueValue;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class UserRequest {
    @NotBlank
    @JsonProperty
    private String name;
    @NotBlank
    @JsonProperty
    @Email
    @UniqueValue(domainClass = User.class,domainField = "email")
    private String email;
    @NotBlank
    @JsonProperty
    @CPF
    @UniqueValue(domainClass = User.class,domainField = "cpf")
    private String cpf;
    @JsonProperty
    @NotBlank
    private String  password;

    public UserRequest(String name, String email, String cpf, String password) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
    }

    public User toUser(){

        return new User(name,email,cpf,password);
    }


}
