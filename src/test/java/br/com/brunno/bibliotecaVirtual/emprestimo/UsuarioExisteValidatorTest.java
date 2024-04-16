package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.usuario.SenhaLimpa;
import br.com.brunno.bibliotecaVirtual.usuario.Tipo;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

class UsuarioExisteValidatorTest {

    public static final String EMAIL = "test@email.com";

    UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    UsuarioExisteValidator usuarioExisteValidator = new UsuarioExisteValidator(usuarioRepository, httpServletRequest);

    @DisplayName("caso ja tenha erro então não deve fazer validacao")
    @Test
    void test() {
        Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "objectName"));
        errors.reject(null, "some other error");
        usuarioExisteValidator.validate(new Object(), errors);

        assertEquals(1, errors.getErrorCount());
        Mockito.verify(errors, Mockito.never()).reject(null, "Prazo de emprestimo invalido para o usuario");
    }

    @DisplayName("Caso usuario não exista então é invalido")
    @Test
    void test1() {
        doReturn(EMAIL).when(httpServletRequest).getHeader("X-EMAIL");
        doReturn(Optional.empty()).when(usuarioRepository).findByEmail(EMAIL);
        Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "objectName"));
        NovoEmprestimoResquest novoEmprestimoResquest = new NovoEmprestimoResquest(40);

        usuarioExisteValidator.validate(novoEmprestimoResquest, errors);

        Mockito.verify(usuarioRepository).findByEmail(EMAIL);
        assertTrue(errors.hasErrors());
    }

    @DisplayName("Caso o usuario exista então é valido")
    @Test
    void test2() {
        doReturn(EMAIL).when(httpServletRequest).getHeader("X-EMAIL");
        Usuario usuario = new Usuario(EMAIL, Tipo.PADRAO, new SenhaLimpa("123456"));
        doReturn(Optional.of(usuario)).when(usuarioRepository).findByEmail(EMAIL);
        Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "objectName"));
        NovoEmprestimoResquest novoEmprestimoResquest = new NovoEmprestimoResquest(40);

        usuarioExisteValidator.validate(novoEmprestimoResquest, errors);

        Mockito.verify(usuarioRepository).findByEmail(EMAIL);
        assertEquals(0, errors.getErrorCount());
    }

}