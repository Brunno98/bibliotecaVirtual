package br.com.brunno.bibliotecaVirtual.livro;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CriaNovoLivroTest {

    LivroRepository livroRepository = Mockito.mock(LivroRepository.class);
    LivroUnicoValidator livroUnicoValidator = new LivroUnicoValidator(livroRepository);
    CriaNovoLivro criaNovoLivro = new CriaNovoLivro(livroRepository, livroUnicoValidator);

    @Test
    @DisplayName("Nao deve ser possivel criar um livro que ja existe")
    void test() {
        Livro livro = new Livro("titulo", BigDecimal.TEN, "isbn");
        Mockito.doReturn(Optional.of(livro)).when(livroRepository).findByTituloOrIsbn("titulo", "isbn");
        NovoLivroRequest request = new NovoLivroRequest("titulo", BigDecimal.TEN, "isbn");

        Assertions.assertThatIllegalArgumentException()
                        .isThrownBy(() -> criaNovoLivro.executa(request));
    }

}