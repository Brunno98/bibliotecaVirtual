package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.livro.Livro;
import br.com.brunno.bibliotecaVirtual.livro.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class NovoExemplarController {

    private final LivroRepository livroRepository;
    private final CadastraNovoExemplar cadastraNovoExemplar;

    public NovoExemplarController(LivroRepository livroRepository, CadastraNovoExemplar cadastraNovoExemplar) {
        this.livroRepository = livroRepository;
        this.cadastraNovoExemplar = cadastraNovoExemplar;
    }

    @PostMapping("/livro/{isbn}/exemplar")
    public NovoExemplarResponse cadatraNovoExemplar(
            @PathVariable String isbn,
            @RequestBody @Valid NovoExemplarRequest novoExemplarRequest
    ) {
        Supplier<Livro> livroSupplier = () -> {
            Optional<Livro> possivelLivro = livroRepository.findByIsbn(isbn);
            if (possivelLivro.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return possivelLivro.get();
        };

        Exemplar exemplar = cadastraNovoExemplar.executa(livroSupplier, novoExemplarRequest);

        return new NovoExemplarResponse(exemplar);
    }
}
