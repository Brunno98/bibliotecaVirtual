package br.com.brunno.bibliotecaVirtual.livro;

import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
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

        Usuario.Tipo tipo = usuario.getTipo();
        if (Usuario.Tipo.PESQUISADOR.equals(tipo)) {
            Integer diasDeEmprestimo = resquest.getDiasDeEmprestimo() == null ? 60 : resquest.getDiasDeEmprestimo();

            Optional<Exemplar> possivelExemplar = livro.getExemplar(exemplar -> true); //Pesquisador pode pegar qualquer exemplar
            if (possivelExemplar.isEmpty()) {
                BindingResult errors = new BeanPropertyBindingResult(null, "livro");
                errors.reject(null, "não há exemplares disponiveis para esse livro");
                throw new BindException(errors);
            }
            Exemplar exemplar = possivelExemplar.get();

            Emprestimo emprestimo = new Emprestimo(usuario, exemplar, diasDeEmprestimo);

            entityManager.persist(emprestimo);

            return emprestimo.toString();
        } else {
            Integer diasDeEmprestimo = resquest.getDiasDeEmprestimo();

            if (usuario.quantidadeDeEmprestimo() >= 5) {
                BindingResult errors = new BeanPropertyBindingResult(null, "usuario");
                errors.reject(null, "usuario já possui 5 emprestimos, não podendo ter mais");
                throw new BindException(errors);
            }

            Optional<Exemplar> possivelExemplar = livro.getExemplar(exemplar -> exemplar.is(Exemplar.Tipo.LIVRE));
            if (possivelExemplar.isEmpty()) {
                BindingResult errors = new BeanPropertyBindingResult(null, "livro");
                errors.reject(null, "não há exemplares disponiveis para esse livro");
                throw new BindException(errors);
            }
            Exemplar exemplar = possivelExemplar.get();

            Emprestimo emprestimo = new Emprestimo(usuario, exemplar, diasDeEmprestimo);

            entityManager.persist(emprestimo);

            return emprestimo.toString();
        }
    }

}
