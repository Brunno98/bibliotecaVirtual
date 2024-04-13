package br.com.brunno.bibliotecaVirtual.livro;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LivroUnicoValidator {

    private final LivroRepository livroRepository;

    public LivroUnicoValidator(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void validate(DadosNovoLivro dadosNovoLivro, Runnable invalidateHandler) {
        Optional<Livro> possivelLivro = livroRepository.findByTituloOrIsbn(dadosNovoLivro.getTitulo(), dadosNovoLivro.getIsbn());
        if (possivelLivro.isPresent()) {
            invalidateHandler.run();
        }
    }

}
