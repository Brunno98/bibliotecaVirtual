package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.compartilhado.Exists;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class DevolucaoEmprestimoController {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoRepository emprestimoRepository;

    public DevolucaoEmprestimoController(UsuarioRepository usuarioRepository, EmprestimoRepository emprestimoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    @PostMapping("/emprestimo/{id}/devolucao")
    public String devolcao(
            @PathVariable @Exists(domain = Emprestimo.class, domainField = "id") Long id,
            @RequestHeader("X-EMAIL") @Exists(domain = Usuario.class, domainField = "email") String email
        ) throws Exception{
        Optional<Emprestimo> possivelEmprestimo = emprestimoRepository.findById(id);
        Assert.isTrue(possivelEmprestimo.isPresent(), "Emprestimo de id "+id+" deveria existir");
        Emprestimo emprestimo = possivelEmprestimo.get();

        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(email);
        Assert.isTrue(possivelUsuario.isPresent(), "Usuario "+email+" deveria existir");
        Usuario usuario = possivelUsuario.get();

        if (!emprestimo.feitoPor(usuario)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (emprestimo.foiDevolvido()) {
            BeanPropertyBindingResult devolucaoDeEmprestimo = new BeanPropertyBindingResult(null, "devolucaoDeEmprestimo");
            devolucaoDeEmprestimo.reject(null, "Emprestimo ja foi devolvido");
            throw new BindException(devolucaoDeEmprestimo);
        }

        emprestimo.devolver();

        emprestimoRepository.save(emprestimo);

        return emprestimo.toString();
    }

}
