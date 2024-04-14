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
public class PrazoDeEmprestimoValidator implements Validator {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    private UsuarioRepository usuarioRepository;

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
            Optional.ofNullable(request.getDiasDeEmprestimo()).ifPresentOrElse(diasDeEmprestimo -> {
                if (diasDeEmprestimo > 60) {
                    errors.rejectValue("diasDeEmprestimo", null, "Dias de emprestimo pode ser no maximo 60");
                }
            }, () -> {
                if (usuario.is(Usuario.Tipo.PADRAO)) {
                    errors.rejectValue("diasDeEmprestimo", null, "É obrigatorio especificar os dias de emprestimo");
                }
            });
        });
    }
}
