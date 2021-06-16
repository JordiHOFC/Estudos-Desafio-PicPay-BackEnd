package br.com.desafiobackend.picpay.transacoes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {
    @JsonProperty
    @NotBlank
    private String username;
    @JsonProperty
    @NotBlank
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UsernamePasswordAuthenticationToken paraAuthentication(){
        return new UsernamePasswordAuthenticationToken(username,password);
    }
}
