package br.com.desafiobackend.picpay.transacoes.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtTokens jwtTokens;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtTokens jwtTokens, AuthenticationManager authenticationManager) {
        this.jwtTokens = jwtTokens;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authentication = request.paraAuthentication();
        Authentication auth = authenticationManager.authenticate(authentication);
        String token = jwtTokens.gerarToken(auth);
        return ResponseEntity.status(OK).header("Authorization", token).body(new AuthenticationResponse(token));

    }
}
