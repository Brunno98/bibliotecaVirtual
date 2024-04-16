package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.livro.Livro;
import br.com.brunno.bibliotecaVirtual.livro.LivroRepository;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import br.com.brunno.bibliotecaVirtual.usuario.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class EmprestimoController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private PrazoDeEmprestimoValidator prazoDeEmprestimoValidator;
    @Autowired
    private UsuarioExisteValidator usuarioExisteValidator;

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(prazoDeEmprestimoValidator);
        binder.addValidators(usuarioExisteValidator);
    }

    @Transactional
    //TODO: adicionar handler de MissingRequestHeaderException
    @PostMapping("/livro/{isbn}/emprestimo")
    public String novoEmprestimo(
            @RequestHeader("X-EMAIL") String email,
            @PathVariable String isbn,
            @RequestBody @Valid NovoEmprestimoResquest resquest
    ) throws BindException {
        Optional<Livro> possivelLivro = livroRepository.findByIsbn(isbn);
        if (possivelLivro.isEmpty()) {
            BindingResult errors = new BeanPropertyBindingResult(isbn, "livro");
            errors.reject(null, "livro com isbn "+isbn+" nao existe");
            throw new BindException(errors);
        }
        Livro livro = possivelLivro.get();

        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(email);
        Usuario usuario = possivelUsuario.get();

        Integer prazoDeEmprestimo = resquest.getDiasDeEmprestimo();

        Emprestimo emprestimo = usuario.novoEmprestimo(livro, prazoDeEmprestimo);
        entityManager.persist(emprestimo);
        return emprestimo.toString();
    }

}
