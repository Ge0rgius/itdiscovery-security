package it.discovery.security.web;

import it.discovery.security.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<LoginDTO> jacksonTester;

    @Test
    void login_userNameExist_tokenGenerated() throws Exception {
        LoginDTO loginDTO = new LoginDTO("Donald");

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonTester.write(loginDTO).getJson()))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void login_userNameMissing_badRequest() throws Exception {
        LoginDTO loginDTO = new LoginDTO("");

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonTester.write(loginDTO).getJson()))
                .andExpect(status().isBadRequest())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION));
    }
}