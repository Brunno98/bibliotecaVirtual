package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.livro.Livro;

public interface DadosNovoExemplar {
    Exemplar toExemplar(Livro livro);
}
