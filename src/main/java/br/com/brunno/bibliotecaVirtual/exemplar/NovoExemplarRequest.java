package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.livro.Livro;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

public class NovoExemplarRequest implements DadosNovoExemplar{

    @NotNull
    private Tipo tipo;

    @Override
    public Exemplar toExemplar(Livro livro) {
        Assert.notNull(livro, "Dados de novo exemplar precisa receber um livro existe para converter para um Exemplar");
        return new Exemplar(livro, tipo);
    }

    public Tipo getTipo() {
        return tipo;
    }
}
