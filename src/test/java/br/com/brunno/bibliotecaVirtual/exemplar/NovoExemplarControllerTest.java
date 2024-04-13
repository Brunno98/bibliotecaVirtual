package br.com.brunno.bibliotecaVirtual.exemplar;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.EdgeCasesMode;
import net.jqwik.api.ForAll;
import net.jqwik.api.GenerationMode;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class NovoExemplarControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Label("Deve criar um novo exemplar")
    @Property(tries = 20, generation = GenerationMode.RANDOMIZED, edgeCases = EdgeCasesMode.FIRST)
    void criaNovoExemplar(
            @ForAll @NotBlank Exemplar.Tipo tipo
    ) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "titulo", "foo",
                                "preco", 42,
                                "isbn", "ABCD"
                        ))));

        mockMvc.perform(MockMvcRequestBuilders.post("/livro/ABCD/exemplar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "tipo", tipo
                        ))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Caso um livro não exista, então deve retorna not found")
    void livroNaoEncontrado() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/livro/ABCD/exemplar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "tipo", "LIVRE"
                ))))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Quando houver erro de validacao, entao deve-se retornar badRequest")
    void badRequest() throws Exception{
        //TODO: o erro de validacao esta no jackson não conseguir transformar "" (vazio) em um Exemplar.Tipo
        // Ao meu ver, seria interante ter uma mensagem de retorno melhor explicativa
        mockMvc.perform(MockMvcRequestBuilders.post("/livro/ABCD/exemplar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "tipo", "" // Campo tipo é obrigatorio
                        ))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}