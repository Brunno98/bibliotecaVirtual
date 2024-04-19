package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsuarioTemEmprestimoExpiradoValidator implements Validator {

    private final UsuarioRepository usuarioRepository;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public UsuarioTemEmprestimoExpiradoValidator(UsuarioRepository usuarioRepository, HttpServletRequest httpServletRequest) {
        this.usuarioRepository = usuarioRepository;
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NovoEmprestimoResquest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        String email = httpServletRequest.getHeader("X-EMAIL");

        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            if (usuario.temEmprestimoExpirado()) {
                errors.reject(null, "Usuario não pode pegar novo emprestimo tendo algum expirado");
            }
        });

    }
}
