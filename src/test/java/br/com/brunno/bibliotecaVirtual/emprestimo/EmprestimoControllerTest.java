package br.com.brunno.bibliotecaVirtual.emprestimo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmprestimoControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Deve realizar o emprestimo do livro")
    @Test
    void test() throws Exception{
        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "email", "test@email.com",
                        "senha", "123456",
                        "tipo", "PADRAO"
                ))));

        mockMvc.perform(post("/livro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "titulo", "livro de test",
                        "preco", 42,
                        "isbn", "ABCDE"
                ))));

        mockMvc.perform(post("/livro/ABCDE/exemplar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "tipo", "LIVRE"
                ))));

        mockMvc.perform(post("/livro/ABCDE/emprestimo")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-EMAIL", "test@email.com")
                .content(objectMapper.writeValueAsBytes(Map.of(
                        "diasDeEmprestimo",60
                ))))
                .andExpect(status().isOk());
    }

    @DisplayName("Caso o livro não seja encontrado, então deve retornar BAD REQUEST")
    @Test
    void test1() throws Exception {
        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "email", "test@email.com",
                        "senha", "123456",
                        "tipo", "PADRAO"
                ))));

        mockMvc.perform(post("/livro/ABCDE/emprestimo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-EMAIL", "test@email.com")
                        .content(objectMapper.writeValueAsBytes(Map.of(
                                "diasDeEmprestimo",60
                        ))))
                .andExpect(status().isBadRequest());
    }
}