package br.com.desafiobackend.picpay.transacoes.config;

import br.com.desafiobackend.picpay.transacoes.users.User;
import br.com.desafiobackend.picpay.transacoes.users.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.*;

@Component
public class JwtTokens {
    @Value("${picpay.api.secret}")
    private String secret;

    @Value("${picpay.api.expiration}")
    private Long expirationInMilliSeconds;

    private final UserRepository repository;


    public JwtTokens(UserRepository repository) {
        this.repository = repository;
    }
    public String gerarToken(Authentication auth){
        final User principal = (User) auth.getPrincipal();
        final Date now = new Date();
        return Jwts.builder()
                .setIssuer("PICPAY API") // setando quem criou o token
                .setSubject(principal.getId().toString()) //setando o id do usuario
                .setIssuedAt(now) //set data de criacao
                .setExpiration(new Date(now.getTime()+expirationInMilliSeconds))// setando a data de expiracao
                .signWith(HS256,secret)// setando qual algoritmo de encryptação e a senha
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getPrincipal(String token) {
        Long id = Long.valueOf(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        return repository.findById(id).get();
    }
}
