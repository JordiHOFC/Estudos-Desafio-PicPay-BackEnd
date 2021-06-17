package br.com.desafiobackend.picpay.transacoes.users;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transacao {

    @Id
    private String id= UUID.randomUUID().toString();

    @ManyToOne
    private Carteira pagador;

    @ManyToOne
    private Carteira beneficiado;

    @CreationTimestamp
    private LocalDateTime transferidoEm=LocalDateTime.now();

    private BigDecimal valor;

    public Transacao(User pagador, User beneficiado, BigDecimal valor) {
        this.pagador = pagador.getCarteira();
        this.beneficiado = beneficiado.getCarteira();
        this.valor=valor;

    }

    public Transacao() {
    }

    public String getId() {
        return id;
    }

    public Carteira getPagador() {
        return pagador;
    }

    public Carteira getBeneficiado() {
        return beneficiado;
    }

    public LocalDateTime getTransferidoEm() {
        return transferidoEm;
    }



    public BigDecimal getValor() {
        return valor;
    }
}
