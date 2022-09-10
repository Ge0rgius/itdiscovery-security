package it.discovery.security.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

//    @MockBean
//    TokenValidator tokenValidator;

//    @Test
//    void findAll_noAuthorizationHeader_unauthorized() throws Exception {
//        BDDMockito.given(tokenValidator.validate(any())).willThrow(new JwtException("No authorization header!"));
//
//        mockMvc.perform(get("/books")).andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    void findAll_userAuthorized_success() throws Exception {
//        BDDMockito.given(tokenValidator.validate(any())).willReturn("Sergio");
//
//        mockMvc.perform(get("/books")).andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }

    @Test
    @WithAnonymousUser
    void findAll_anonymousUser_unauthorized() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin")
    void findAll_userAuthorized_success() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}