package br.com.desafiobackend.picpay.transacoes.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

import static javax.persistence.CascadeType.*;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column
    private String name;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String documento;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Column
    private String password;

    @OneToOne(mappedBy = "portador", cascade = {PERSIST,MERGE})
    private Carteira carteira;


    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    public User(String name, String documento, String email, String password, TipoConta tipoConta) {
        this.name = name;
        this.documento = documento;
        this.email = email;
        this.password = password;
        this.tipoConta=tipoConta;
        this.carteira=new Carteira(this,new BigDecimal("0.0"));
    }

    public User() {

    }
    public void depositar(BigDecimal valor, Transacao transacao){
        carteira.depositar(valor, transacao);

    }
    public void sacar(BigDecimal valor, Transacao transacao){
        carteira.sacar(valor,transacao);
    }
    public BigDecimal getSaldo(){
        return carteira.getSaldo();
    }

    public Long getId() {
        return id;
    }

    public Carteira getCarteira() {
        return this.carteira;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
