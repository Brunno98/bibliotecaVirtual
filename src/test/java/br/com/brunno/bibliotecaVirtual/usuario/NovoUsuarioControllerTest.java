package br.com.brunno.bibliotecaVirtual.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class NovoUsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private Set<String> emailsUsados = new HashSet<>();

    @Label("Deve criar novo usuario")
    @Property(tries = 20)
    void novoUsuario(
            @ForAll @StringLength(min=3, max=50) @AlphaChars @NotBlank String email,
            @ForAll @StringLength(min=6, max=255) @AlphaChars @NumericChars String senha,
            @ForAll Tipo tipo
    ) throws Exception {
        Assumptions.assumeTrue(emailsUsados.add(email));

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "email", email+"@email.com",
                        "senha", senha,
                        "tipo", tipo
                ))))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "email", email+"@email.com",
                                "senha", senha,
                                "tipo", tipo
                        ))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}