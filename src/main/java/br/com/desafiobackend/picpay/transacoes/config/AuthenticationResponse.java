package br.com.desafiobackend.picpay.transacoes.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty
    private String tipo="Bearer ";
    @JsonProperty
    private String token;

    public AuthenticationResponse(String token) {
        this.token=token;
    }
}
