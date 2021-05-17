package com.safayildirim.authservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safayildirim.authservice.controllers.UserController;
import com.safayildirim.authservice.dto.UserRegisterRequest;
import com.safayildirim.authservice.services.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;

    @Mock
    private AuthenticationService authenticationService;

    @Before
    public void setup() {
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("zeevac");
        request.setPassword("112233");
        request.setEmail("safayildirim54@gmail.com");
        mockMvc.perform((post("/register").param("username", "zeevac"))
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
}
