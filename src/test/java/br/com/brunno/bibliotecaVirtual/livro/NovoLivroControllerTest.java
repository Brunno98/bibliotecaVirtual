package br.com.brunno.bibliotecaVirtual.livro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.constraints.Scale;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Whitespace;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class NovoLivroControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Set<String> titulosUsados = new HashSet<>();
    Set<String> isbnUsados = new HashSet<>();

    @Property(tries = 20)
    @Label("Deve criar um novo livro")
    void createBook(
            @ForAll @StringLength(min=1, max=255) @AlphaChars @NotBlank String titulo,
            @ForAll @BigRange(max="999") @Positive @Scale(2) BigDecimal preco,
            @ForAll @StringLength(min=1, max=255) @AlphaChars @NotBlank String isbn
    ) throws Exception {
        Assumptions.assumeTrue(titulosUsados.add(titulo));
        Assumptions.assumeTrue(isbnUsados.add(isbn));

        mockMvc.perform(MockMvcRequestBuilders.post("/livro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "titulo", titulo,
                        "preco", preco,
                        "isbn", isbn
                ))))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "titulo", titulo,
                                "preco", preco,
                                "isbn", isbn
                        ))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // se livro ja existir entao retorna bad request
    }

}