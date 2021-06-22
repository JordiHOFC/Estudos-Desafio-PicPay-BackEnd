package br.com.desafiobackend.picpay.transacoes.users;

import br.com.desafiobackend.picpay.transacoes.validators.ExistId;
import br.com.desafiobackend.picpay.transacoes.validators.SaldoDisponivel;
import br.com.desafiobackend.picpay.transacoes.validators.IsNaoLojista;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
@SaldoDisponivel
public class TransacaoRequest {
    @JsonProperty
    @Positive
    @NotNull
    private BigDecimal valor;

    @JsonProperty
    @IsNaoLojista
    @NotNull
    @ExistId
    private Long pagador;
    @JsonProperty
    @NotNull
    @ExistId
    private Long beneficiado;

    public TransacaoRequest(BigDecimal valor, Long pagador, Long beneficiado) {
        this.valor = valor;
        this.pagador = pagador;
        this.beneficiado = beneficiado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Long getPagador() {
        return pagador;
    }

    public Long getBeneficiado() {
        return beneficiado;
    }
}
