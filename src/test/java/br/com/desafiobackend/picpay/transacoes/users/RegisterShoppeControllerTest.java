package br.com.desafiobackend.picpay.transacoes.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
class RegisterShoppeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EntityManager manager;

    @Test
    @DisplayName("deve cadastrar um Shoppe com todas informações validas")
    public void test() throws Exception {
        ShoppeRequest acer = new ShoppeRequest("Acer", "noreply@acer.com", "03.860.063/0001-06", "123456");
        String requestJson = mapper.writeValueAsString(acer);
        URI uri = URI.create("http://localhost:8080/shoppes");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/shoppes/*"));

    }

    @Test
    @DisplayName("nao deve cadastrar um Shoppe com email invalidas")
    public void test1() throws Exception {
        ShoppeRequest acer = new ShoppeRequest("Acer", "noreplyacer.com", "35.572.829/0001-43", "123456");
        String requestJson = mapper.writeValueAsString(acer);
        URI uri = URI.create("http://localhost:8080/shoppes");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("nao deve cadastrar um Shoppe com cnpj invalidas")
    public void test2() throws Exception {
        ShoppeRequest acer = new ShoppeRequest("Acer", "noreply@acer.com.br", "35.572.8521/0001-43", "123456");
        String requestJson = mapper.writeValueAsString(acer);
        URI uri = URI.create("http://localhost:8080/shoppes");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("nao deve cadastrar um Shoppe com todas informações em branco")
    public void test3() throws Exception {
        ShoppeRequest acer = new ShoppeRequest(null, null, "", "");
        String requestJson = mapper.writeValueAsString(acer);
        URI uri = URI.create("http://localhost:8080/shoppes");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("nao deve cadastrar um Shoppe com email já cadastado")
    @Transactional
    public void test4() throws Exception {
        ShoppeRequest acer = new ShoppeRequest("Acer", "noreplyNotify@acer.com.br", "09.668.407/0001-66", "123456");
        Shoppe shoppe = acer.toShoppe();
        manager.persist(shoppe);
        String requestJson = mapper.writeValueAsString(acer);
        URI uri = URI.create("http://localhost:8080/shoppes");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("nao deve cadastrar um Shoppe com cnpj já cadastado")
    @Transactional
    public void test5() throws Exception {
        ShoppeRequest acer = new ShoppeRequest("Acer", "noreply@acer.com.br", "09.668.407/0001-66", "123456");
        Shoppe shoppe = acer.toShoppe();
        manager.persist(shoppe);
        String requestJson = mapper.writeValueAsString(acer);
        URI uri = URI.create("http://localhost:8080/shoppes");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isBadRequest());
    }


}