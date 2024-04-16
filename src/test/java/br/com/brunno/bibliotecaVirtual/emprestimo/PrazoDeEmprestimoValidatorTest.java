package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.usuario.SenhaLimpa;
import br.com.brunno.bibliotecaVirtual.usuario.Tipo;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class PrazoDeEmprestimoValidatorTest {

    public static final String EMAIL = "test@email.com";

    UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    PrazoDeEmprestimoValidator prazoDeEmprestimoValidator = new PrazoDeEmprestimoValidator(httpServletRequest, usuarioRepository);

    @DisplayName("caso ja tenha erro então não deve fazer validacao")
    @Test
    void test() {
        Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "objectName"));
        errors.reject(null, "some other error");
        prazoDeEmprestimoValidator.validate(new Object(), errors);

        assertEquals(1, errors.getErrorCount());
        Mockito.verify(errors, Mockito.never()).reject(null, "Prazo de emprestimo invalido para o usuario");
    }

    @DisplayName("Valida de acordo com o prazo de entrega passado")
    @ParameterizedTest
    @CsvSource({
            "60,PADRAO,true",
            "61,PADRAO,false",
            "60,PESQUISADOR,true",
            "61,PESQUISADOR,false",
    })
    void test2(Integer prazoDeEntrega, Tipo tipo, boolean esperaSerValido) {
        doReturn(EMAIL).when(httpServletRequest).getHeader("X-EMAIL");
        Usuario usuario = new Usuario(EMAIL, Tipo.PADRAO, new SenhaLimpa("123456"));
        doReturn(Optional.of(usuario)).when(usuarioRepository).findByEmail(EMAIL);
        Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "objectName"));
        NovoEmprestimoResquest novoEmprestimoResquest = new NovoEmprestimoResquest(prazoDeEntrega);

        prazoDeEmprestimoValidator.validate(novoEmprestimoResquest, errors);

        if (esperaSerValido) {
            assertEquals(0, errors.getErrorCount());
            Mockito.verify(errors, Mockito.never()).reject(null, "Prazo de emprestimo invalido para o usuario");
        } else {
            assertEquals(1, errors.getErrorCount());
            Mockito.verify(errors).reject(null, "Prazo de emprestimo invalido para o usuario");
        }
    }

}