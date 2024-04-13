package br.com.brunno.bibliotecaVirtual.livro;

import org.springframework.stereotype.Service;

@Service
public class CriaNovoLivro {

    private final LivroRepository livroRepository;
    private final LivroUnicoValidator livroUnicoValidator;

    public CriaNovoLivro(LivroRepository livroRepository, LivroUnicoValidator livroUnicoValidator) {
        this.livroRepository = livroRepository;
        this.livroUnicoValidator = livroUnicoValidator;
    }

    public Livro executa(DadosNovoLivro dadosNovoLivro) {
        livroUnicoValidator.validate(dadosNovoLivro, () -> {
            throw new IllegalArgumentException("Já existe livro com este titulo ou isbn");
        });

        Livro livro = dadosNovoLivro.toLivro();
        return livroRepository.save(livro);
    }

}
