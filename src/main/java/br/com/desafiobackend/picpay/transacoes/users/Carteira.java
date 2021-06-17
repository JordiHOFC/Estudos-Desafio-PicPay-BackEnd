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

    @OneToMany(mappedBy = "beneficiado",cascade = {PERSIST,MERGE})
    private List<Transacao> recebimentos=new ArrayList<>();

    @OneToMany(mappedBy = "pagador",cascade = {PERSIST,MERGE})
    private List<Transacao> pagamentos=new ArrayList<>();


    public Carteira(User portador, BigDecimal saldo) {
        this.portador = portador;
        this.saldo = saldo;
    }

    public Carteira() {
    }

    public void depositar(BigDecimal valor, Transacao transacao){
        this.saldo=saldo.add(valor);
        associarDeposito(transacao);
    }
    public void sacar(BigDecimal valor, Transacao transacao){
        this.saldo=saldo.subtract(valor);
        associarPagamento(transacao);

    }

    public void associarDeposito(Transacao transacao){
        this.recebimentos.add(transacao);
    }
    public void associarPagamento(Transacao transacao){ this.pagamentos.add(transacao);}

    public BigDecimal getSaldo() {
        return saldo;
    }
    public List<Transacao> getTransacoes(){
        List<Transacao> transacoes= new ArrayList<>();
        transacoes.addAll(recebimentos);
        transacoes.addAll(pagamentos);
        return transacoes;
    }
}
