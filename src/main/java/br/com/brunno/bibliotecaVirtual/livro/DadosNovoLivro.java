package br.com.brunno.bibliotecaVirtual.livro;

public interface DadosNovoLivro {

    Livro toLivro();

    String getIsbn();

    String getTitulo();
}
