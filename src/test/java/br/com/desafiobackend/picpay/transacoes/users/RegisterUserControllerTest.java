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

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class RegisterUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EntityManager manager;


    @Test
    @DisplayName("deve cadastrar um User com payload valido")
    public void test1() throws Exception {

        UserRequest userRequest = new UserRequest("Jordi Henrique M. Silva", "jordi2@email.com", "884.258.320-04", "123456");
        String requestJson = mapper.writeValueAsString(userRequest);
        System.out.println(requestJson);
        URI uri = URI.create("http://localhost:8080/users");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/users/*"));
    }

    @Test
    @DisplayName("nao deve cadatrar um User com email invalido")
    public void test2() throws Exception {
        UserRequest userRequest = new UserRequest("Jordi Henrique M. Silva", "jordiemail.com", "385.481.660-02", "123456");
        String requestJson = mapper.writeValueAsString(userRequest);
        System.out.println(requestJson);
        URI uri = URI.create("http://localhost:8080/users");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());

    }
    @Test
    @DisplayName("nao deve cadatrar um User com cpf invalido")
    public void test3() throws Exception {
        UserRequest userRequest = new UserRequest("Jordi Henrique M. Silva", "henrique@email.com", "929399.360-05", "123456");
        String requestJson = mapper.writeValueAsString(userRequest);
        System.out.println(requestJson);
        URI uri = URI.create("http://localhost:8080/users");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("nao deve cadastrar um User com campos em branco")
    public void test4() throws Exception {
        UserRequest userRequest = new UserRequest(" ", " ", null, null);
        String requestJson = mapper.writeValueAsString(userRequest);
        System.out.println(requestJson);
        URI uri = URI.create("http://localhost:8080/users");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());

    }
    @Test
    @DisplayName("nao deve cadastrar um User com email já  em cadastrado")
    @Transactional
    public void test5() throws Exception {
        UserRequest userRequest = new UserRequest("Jordi Silva ", "jordi@gmail.com ", "785.339.270-29", "123456");
        User jordiSilva = userRequest.toUser();
        manager.persist(jordiSilva);
        String requestJson = mapper.writeValueAsString(userRequest);
        System.out.println(requestJson);
        URI uri = URI.create("http://localhost:8080/users");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());

    }
    @Test
    @DisplayName("nao deve cadastrar um User com cpf já  em cadastrado")
    @Transactional
    public void test6() throws Exception {
        UserRequest userRequest = new UserRequest("Jordi Silva ", "jordi@live.com ", "785.339.270-29", "123456");
        User jordiSilva = userRequest.toUser();
        manager.persist(jordiSilva);
        String requestJson = mapper.writeValueAsString(userRequest);
        System.out.println(requestJson);
        URI uri = URI.create("http://localhost:8080/users");
        mockMvc.perform(post(uri)
                .contentType(APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());

    }
}