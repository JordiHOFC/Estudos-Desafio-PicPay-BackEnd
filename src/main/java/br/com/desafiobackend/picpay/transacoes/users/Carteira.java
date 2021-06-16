package br.com.desafiobackend.picpay.transacoes.users;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
public class Carteira {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @MapsId
    private User portador;

    private BigDecimal saldo=new BigDecimal("0.0");

    @OneToMany(mappedBy = "carteira", cascade = {PERSIST,MERGE})
    private List<Transacao> transacoes=new ArrayList<>();

    public Carteira(User portador, BigDecimal saldo) {
        this.portador = portador;
        this.saldo = saldo;
    }

    public Carteira() {
    }

    public void depositar(BigDecimal valor, Transacao transacao){
        this.saldo=saldo.add(valor);
        associarTransacao(transacao);
    }
    public void sacar(BigDecimal valor, Transacao transacao){
        this.saldo=saldo.subtract(valor);
        associarTransacao(transacao);

    }

    public void associarTransacao(Transacao transacao){
        this.transacoes.add(transacao);
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
