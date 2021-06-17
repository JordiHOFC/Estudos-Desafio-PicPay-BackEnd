package br.com.desafiobackend.picpay.transacoes.compartilhado;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class ControleDeTransacaoPragmatico {
    @PersistenceContext
    private EntityManager manager;


    @Transactional
    public <T> T salvaEComita(T objeto) {
        manager.persist(objeto);
        return objeto;
    }

    @Transactional
    public <T> T atualizaEComita(T objeto) {
        return manager.merge(objeto);
    }

    @Transactional
    public <T> T executa(Supplier<T> algumCodigoComRetorno){
        return algumCodigoComRetorno.get();
    }

}

