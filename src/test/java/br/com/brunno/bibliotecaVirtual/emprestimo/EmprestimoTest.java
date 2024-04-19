package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import br.com.brunno.bibliotecaVirtual.usuario.SenhaLimpa;
import br.com.brunno.bibliotecaVirtual.usuario.Tipo;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

class EmprestimoTest {

    Usuario usuario = new Usuario("test@email.com", Tipo.PADRAO, new SenhaLimpa("123456"));
    Livro livro = new Livro("titulo", BigDecimal.TEN, "isbn");
    Exemplar exemplar = new Exemplar(livro, br.com.brunno.bibliotecaVirtual.exemplar.Tipo.LIVRE);
    Emprestimo emprestimo = new Emprestimo(usuario, exemplar, 1);

    @DisplayName("Quando a data de prazo de entrega estiver vencida, então o emprestimo está expirado")
    @Test
    void test1() {
        ReflectionTestUtils.setField(emprestimo, "prazoDeEmprestimo", LocalDate.now().minusDays(1));

        Assertions.assertThat(emprestimo.expirado()).isTrue();
    }

    @DisplayName("Quando o emprestimo tiver data de devolcao, mesmo que apos o prazo de entrega, " +
            "entao nao é considerado expirado")
    @Test
    void test2() {
        ReflectionTestUtils.setField(emprestimo, "prazoDeEmprestimo", LocalDate.now().minusDays(2));
        ReflectionTestUtils.setField(emprestimo, "dataDevolucao", LocalDate.now().minusDays(1));

        Assertions.assertThat(emprestimo.expirado()).isFalse();
        Assertions.assertThat(emprestimo.devolvido()).isTrue();
    }

    @DisplayName("Nao deve ser possivel criar emprestimo com dias de entrega negativo")
    @Test
    void test3() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Emprestimo(usuario, exemplar, -1));
    }

}