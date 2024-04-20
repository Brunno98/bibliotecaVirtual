package br.com.brunno.bibliotecaVirtual.emprestimo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DevolucaoEmprestimoControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Deve devolver o emprestimo")
    void test() throws Exception {
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

        MvcResult criaEmprestimoResponse = mockMvc.perform(post("/livro/ABCDE/emprestimo")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-EMAIL", "test@email.com")
                .content(objectMapper.writeValueAsBytes(Map.of(
                        "diasDeEmprestimo", 60
                )))).andReturn();
        Map<String, String> emprestimoResponseBody = objectMapper.readValue(
                criaEmprestimoResponse.getResponse().getContentAsByteArray(), new TypeReference<Map<String, String>>() {});

        String emprestimoId = emprestimoResponseBody.get("id");
        mockMvc.perform(post("/emprestimo/"+emprestimoId+"/devolucao")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-EMAIL", "test@email.com"))
                .andExpect(status().isOk());
    }

}