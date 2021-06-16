package br.com.desafiobackend.picpay.transacoes.config;

import br.com.desafiobackend.picpay.transacoes.users.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokens jwtToken;

    public JwtTokenAuthenticationFilter(JwtTokens jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperaToken(request);
        boolean tokenValid=jwtToken.isValid(token);
        if (tokenValid) {
            User jwtTokenPrincipal = jwtToken.getPrincipal(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtTokenPrincipal.getUsername(), null, jwtTokenPrincipal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
    private String recuperaToken(HttpServletRequest request) {
        String token=request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
