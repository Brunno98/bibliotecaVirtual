package br.com.brunno.bibliotecaVirtual.livro;

import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UsuarioExisteValidator implements Validator {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NovoEmprestimoResquest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        String email = httpServletRequest.getHeader("X-EMAIL");
        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(email);
        if (possivelUsuario.isEmpty()) {
            errors.reject(null, "Usuario "+email+" nao encontrado");
        }
    }
}
