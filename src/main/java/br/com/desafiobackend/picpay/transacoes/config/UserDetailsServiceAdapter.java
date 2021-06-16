package br.com.desafiobackend.picpay.transacoes.config;

import br.com.desafiobackend.picpay.transacoes.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceAdapter implements UserDetailsService {
    private final UserRepository repository;

    public UserDetailsServiceAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Usuario inexistente"));
    }
}
