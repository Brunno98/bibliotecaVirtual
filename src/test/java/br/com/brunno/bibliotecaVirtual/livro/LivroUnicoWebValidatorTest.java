package br.com.brunno.bibliotecaVirtual.livro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

class LivroUnicoWebValidatorTest {

    LivroRepository livroRepository = Mockito.mock(LivroRepository.class);
    LivroUnicoWebValidator livroUnicoWebValidator = new LivroUnicoWebValidator(
            new LivroUnicoValidator(livroRepository)
    );

    @DisplayName("Se ja tiver erro então ignora")
    @Test
    void hasError() {
        Errors errors = new BeanPropertyBindingResult(null, "");
        errors.reject(null, "Some error");
        livroUnicoWebValidator.validate(new Object(), errors);

        Mockito.verify(livroRepository, Mockito.never()).findByTituloOrIsbn(Mockito.anyString(), Mockito.anyString());
    }

}