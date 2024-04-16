package br.com.brunno.bibliotecaVirtual.usuario;

import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

class TipoTest {

    @DisplayName("Deve validar se o prazo de entrega está valido, de acordo com o tipo")
    @ParameterizedTest
    @CsvSource({
            "PADRAO,60,true",
            "PADRAO,61,false",
            "PADRAO,,false",
            "PADRAO,0,false",
            "PADRAO,-1,false",
            "PESQUISADOR,60,true",
            "PESQUISADOR,61,false",
            "PESQUISADOR,,true",
            "PESQUISADOR,0,false",
            "PESQUISADOR,-1,false"
    })
    void test(Tipo tipo, Integer prazo, boolean resultadoExperado) {
        Assertions
                .assertThat(tipo.prazoDeEmprestimoValido(prazo))
                .isEqualTo(resultadoExperado);
    }

    @DisplayName("Deve dizer se o usuario está apto a pegar um novo emprestimo, de acordo com o tipo")
    @ParameterizedTest
    @CsvSource({
            "PADRAO,4,true",
            "PADRAO,5,false",
            "PADRAO,2,true",
            "PESQUISADOR,1,true",
            "PESQUISADOR,5,true",
            "PESQUISADOR,15,true",
    })
    void test2(Tipo tipo, int quantidadeDeEmprestimos, boolean resultadoExperado) {
        Usuario usuario = new Usuario("test@email.com", tipo, new SenhaLimpa("123456"));
        Livro livro = new Livro("Livro test", BigDecimal.TEN, "isbn");
        List<Exemplar> exemplares = new ArrayList<>();
        for (int i = 0; i <quantidadeDeEmprestimos+1; i++) {
            Exemplar exemplar = new Exemplar(livro, br.com.brunno.bibliotecaVirtual.exemplar.Tipo.LIVRE);
            exemplares.add(exemplar);
        }
        ReflectionTestUtils.setField(livro, "exemplares", exemplares);

        for (int i = 0; i < quantidadeDeEmprestimos; i++) {
            usuario.novoEmprestimo(livro, 40);
        }

        Assertions.assertThat(tipo.aceitaNovoEmprestimo(usuario))
                .isEqualTo(resultadoExperado);
    }

}