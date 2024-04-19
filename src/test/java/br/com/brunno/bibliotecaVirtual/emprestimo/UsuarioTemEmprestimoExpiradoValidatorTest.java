package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import br.com.brunno.bibliotecaVirtual.usuario.SenhaLimpa;
import br.com.brunno.bibliotecaVirtual.usuario.Tipo;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoInitializationException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTemEmprestimoExpiradoValidatorTest {

    UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

    UsuarioTemEmprestimoExpiradoValidator validator =
            new UsuarioTemEmprestimoExpiradoValidator(usuarioRepository, httpServletRequest);

    @DisplayName("Caso usuario tenha emprestimo expirado então deve invalidar")
    @Test
    void test1() {
        Usuario usuario = new Usuario("test@email.com", Tipo.PADRAO, new SenhaLimpa("123456"));
        Livro livro = new Livro("titulo", BigDecimal.TEN, "isbn");
        Exemplar exemplar = new Exemplar(livro, br.com.brunno.bibliotecaVirtual.exemplar.Tipo.LIVRE);
        Emprestimo emprestimo = new Emprestimo(usuario, exemplar, 1);
        ReflectionTestUtils.setField(emprestimo, "prazoDeEmprestimo", LocalDate.now().minusDays(1));
        ReflectionTestUtils.setField(usuario, "emprestimos", List.of(emprestimo));
        Errors errors = new BeanPropertyBindingResult(null, "target");
        Mockito.doReturn(Optional.of(usuario)).when(usuarioRepository).findByEmail("test@email.com");
        Mockito.doReturn("test@email.com").when(httpServletRequest).getHeader("X-EMAIL");

        validator.validate(new NovoEmprestimoResquest(1), errors);

        Assertions.assertTrue(errors.hasErrors());
    }

    @DisplayName("Caso usuario não tenha emprestimo expirado então usuario é valido")
    @Test
    void test2() {
        Usuario usuario = new Usuario("test@email.com", Tipo.PADRAO, new SenhaLimpa("123456"));
        Livro livro = new Livro("titulo", BigDecimal.TEN, "isbn");
        Exemplar exemplar = new Exemplar(livro, br.com.brunno.bibliotecaVirtual.exemplar.Tipo.LIVRE);
        Emprestimo emprestimo = new Emprestimo(usuario, exemplar, 1);
        ReflectionTestUtils.setField(usuario, "emprestimos", List.of(emprestimo));
        Errors errors = new BeanPropertyBindingResult(null, "target");
        Mockito.doReturn(Optional.of(usuario)).when(usuarioRepository).findByEmail("test@email.com");
        Mockito.doReturn("test@email.com").when(httpServletRequest).getHeader("X-EMAIL");

        validator.validate(new NovoEmprestimoResquest(1), errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @DisplayName("caso ja tenha erro então não deve fazer validacao")
    @Test
    void test3() {
        Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "objectName"));
        errors.reject(null, "some other error");
        validator.validate(new Object(), errors);

        assertEquals(1, errors.getErrorCount());
        Mockito.verify(errors, Mockito.never()).reject(null, "Prazo de emprestimo invalido para o usuario");
    }

}