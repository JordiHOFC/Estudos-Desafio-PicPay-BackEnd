package br.com.desafiobackend.picpay.transacoes.users;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column
    private String name;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String cpf;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Column
    private String password;

    public User(String name, String cpf, String email, String password) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }
}
