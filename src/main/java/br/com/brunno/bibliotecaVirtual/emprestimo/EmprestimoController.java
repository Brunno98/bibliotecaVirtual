package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.compartilhado.Exists;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import br.com.brunno.bibliotecaVirtual.livro.LivroRepository;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
// 9 > 7
@RestController
public class EmprestimoController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private PrazoDeEmprestimoValidator prazoDeEmprestimoValidator;
    @Autowired
    private UsuarioTemEmprestimoExpiradoValidator usuarioTemEmprestimoExpiradoValidator;

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(prazoDeEmprestimoValidator);
        binder.addValidators(usuarioTemEmprestimoExpiradoValidator);
    }

    @Transactional
    @PostMapping("/livro/{isbn}/emprestimo")
    public NovoEmprestimoResponse novoEmprestimo(
            @RequestHeader("X-EMAIL") @Valid @Exists(domain = Usuario.class, domainField = "email") String email,
            @PathVariable @Valid @Exists(domain = Livro.class, domainField = "isbn") String isbn,
            @RequestBody @Valid NovoEmprestimoResquest resquest
    ) throws BindException {
        Optional<Livro> possivelLivro = livroRepository.findByIsbn(isbn);
        Assert.isTrue(possivelLivro.isPresent(), "Livro com isbn "+isbn+" deveria existir");
        Livro livro = possivelLivro.get();

        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(email);
        Assert.isTrue(possivelUsuario.isPresent(), "Usuario com email "+email+"deveria existir");
        Usuario usuario = possivelUsuario.get();

        Integer prazoDeEmprestimo = resquest.getDiasDeEmprestimo();

        Emprestimo emprestimo = usuario.novoEmprestimo(livro, prazoDeEmprestimo);
        entityManager.persist(emprestimo);
        return new NovoEmprestimoResponse(emprestimo);
    }



}
