package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.validators.DocumentoValido;
import br.com.desafiobackend.picpay.transacoes.validators.UniqueValue;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @DocumentoValido
    @UniqueValue(domainClass = User.class,domainField = "documento")
    private String documento;
    @JsonProperty
    @NotBlank
    private String  password;


    public UserRequest(String name, String email, String documento, String password) {
        this.name = name;
        this.email = email;
        this.documento = documento;
        this.password = password;

    }

    public User toUser(BCryptPasswordEncoder encoder){
        password=encoder.encode(password);
        if(documento.length()==14){
            return new User(name,documento,email,password,TipoConta.PESSOAL);
        }
        return  new User(name,documento,email,password,TipoConta.LOJISTA);
    }


}
