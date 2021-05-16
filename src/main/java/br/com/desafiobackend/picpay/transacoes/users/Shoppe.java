package br.com.desafiobackend.picpay.transacoes.users;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Shoppe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column
    private String name;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String cnpj;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Column
    private String password;

    public Shoppe(String name, String cnpj, String email, String password) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.password = password;
    }
    public Shoppe(){

    }
}
