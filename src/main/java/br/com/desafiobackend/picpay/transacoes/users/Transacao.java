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
    private User pagador;

    @ManyToOne
    private User beneficado;

    @CreationTimestamp
    private LocalDateTime transferidoEm=LocalDateTime.now();

    private BigDecimal valor;

    @ManyToOne
    private Carteira carteira;

    public Transacao(User pagador, User beneficado, Carteira carteira, BigDecimal valor) {
        this.pagador = pagador;
        this.beneficado = beneficado;
        this.carteira = carteira;
        this.valor=valor;

    }

    public Transacao() {
    }

    public String getId() {
        return id;
    }

    public User getPagador() {
        return pagador;
    }

    public User getBeneficado() {
        return beneficado;
    }

    public LocalDateTime getTransferidoEm() {
        return transferidoEm;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
