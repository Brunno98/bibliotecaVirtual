package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PrazoDeEmprestimoValidator implements Validator {

    private final HttpServletRequest httpServletRequest;
    private final UsuarioRepository usuarioRepository;

    public PrazoDeEmprestimoValidator(HttpServletRequest httpServletRequest, UsuarioRepository usuarioRepository) {
        this.httpServletRequest = httpServletRequest;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NovoEmprestimoResquest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;
        NovoEmprestimoResquest request = (NovoEmprestimoResquest) target;

        String email = httpServletRequest.getHeader("X-EMAIL");
        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(email);
        possivelUsuario.ifPresent(usuario -> {
            if (!usuario.prazoDeEmprestimoValido(request.getDiasDeEmprestimo())) {
                errors.reject(null, "Prazo de emprestimo invalido para o usuario");
            }
        });
    }
}
