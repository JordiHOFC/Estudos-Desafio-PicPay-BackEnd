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
    private Carteira beneficado;

    @CreationTimestamp
    private LocalDateTime transferidoEm=LocalDateTime.now();

    private BigDecimal valor;

    public Transacao(User pagador, User beneficado, Carteira carteira, BigDecimal valor) {
        this.pagador = pagador.getCarteira();
        this.beneficado = beneficado.getCarteira();
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

    public Carteira getBeneficado() {
        return beneficado;
    }

    public LocalDateTime getTransferidoEm() {
        return transferidoEm;
    }



    public BigDecimal getValor() {
        return valor;
    }
}
