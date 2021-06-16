package br.com.desafiobackend.picpay.transacoes.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@Transactional
class RegisterUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EntityManager manager;


    @Test
    @DisplayName("deve cadastrar um User  com payload valido")
    public void test1() throws Exception {

        UserRequest pessoafisica = new UserRequest("Jordi Henrique M. Silva", "jordi2@email.com", "884.258.320-04", "123456");
        UserRequest pessoaJuridica = new UserRequest("Jordi Henrique M. Silva", "jordi@email.com", "61.501.644/0001-58", "123456");
        List<UserRequest> requestList = List.of(pessoafisica, pessoaJuridica);
        requestList.forEach(userRequest -> {
            String requestJson = null;
            try {
                requestJson = mapper.writeValueAsString(userRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(requestJson);
            URI uri = URI.create("http://localhost:8080/users");
            try {
                mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                ).andExpect(status().isCreated())
                        .andExpect(redirectedUrlPattern("/users/*"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @DisplayName("nao deve cadastrar um User com payload invalido")
    public void test2(){

        final List<UserRequest> userRequests = errosUsers();
        final User user = userRequests.get(userRequests.size() - 1).toUser(new BCryptPasswordEncoder());
        manager.persist(user);
        userRequests.forEach(userRequest -> {
            String requestJson = null;
            try {
                requestJson = mapper.writeValueAsString(userRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(requestJson);
            URI uri = URI.create("http://localhost:8080/users");
            try {
                mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                ).andExpect(status().isBadRequest());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public List<UserRequest> errosUsers(){
        final List<UserRequest> users = List.of(
                new UserRequest("Jordi Henrique M. Silva", "jordi2@email.com", "884.258.320-04", " "),
                new UserRequest(null, "jordi2@email.com", "884.258.320-04", " "),
                new UserRequest("Jordi Henrique M. Silva", "jordi2@.com", "884.25804", " "),
                new UserRequest("Jordi Henrique M. Silva", "jordi2@e.com", "884.25804", "123456")
        );
        return users;
    }
}
