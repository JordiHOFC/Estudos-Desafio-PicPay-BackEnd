package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.validators.UniqueValue;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class ShoppeRequest {
    @NotBlank
    @JsonProperty
    private String name;
    @NotBlank
    @JsonProperty
    @Email
    @UniqueValue(domainField = "email",domainClass = Shoppe.class)
    private String email;
    @NotBlank
    @JsonProperty
    @CNPJ
    @UniqueValue(domainField = "cnpj",domainClass = Shoppe.class)
    private String cnpj;
    @NotBlank
    @JsonProperty
    private String password;

    public ShoppeRequest(String name, String email, String cnpj, String password) {
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.password = password;
    }
    public Shoppe toShoppe(){
        return new Shoppe(name,cnpj,email,password);
    }
}
