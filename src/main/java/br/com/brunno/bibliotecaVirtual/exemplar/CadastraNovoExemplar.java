package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.livro.Livro;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.function.Supplier;

@Service
public class CadastraNovoExemplar {

    private final ExemplarRepository exemplarRepository;

    public CadastraNovoExemplar(ExemplarRepository exemplarRepository) {
        this.exemplarRepository = exemplarRepository;
    }

    public Exemplar executa(Supplier<Livro> livroSupplier, DadosNovoExemplar dadosNovoExemplar) {
        Livro livro = livroSupplier.get();
        Assert.notNull(livro, "Precisa existir um livro para realizar o cadastro de um novo exemplar");
        Exemplar exemplar = dadosNovoExemplar.toExemplar(livro);
        return exemplarRepository.save(exemplar);
    }
}
