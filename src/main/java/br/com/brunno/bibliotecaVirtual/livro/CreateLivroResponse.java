package br.com.brunno.bibliotecaVirtual.livro;

import java.math.BigDecimal;

public class CreateLivroResponse {
    private final Long id;
    private final String titulo;
    private final BigDecimal preco;
    private final String isbn;

    public CreateLivroResponse(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.preco = livro.getPreco();
        this.isbn = livro.getIsbn();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getIsbn() {
        return isbn;
    }
}
