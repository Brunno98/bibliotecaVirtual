package br.com.brunno.bibliotecaVirtual.livro;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public class NovoLivroRequest implements DadosNovoLivro {

    @NotBlank
    private String titulo;
    @NotNull
    @DecimalMin("0")
    private BigDecimal preco;
    @NotBlank
    private String isbn;

    public NovoLivroRequest(String titulo, BigDecimal preco, String isbn) {
        Assert.hasText(titulo, "Precisa ter um titulo para criar um novo livro");
        Assert.notNull(preco, "Precisa ter um preco para criar um novo livro");
        Assert.hasText(isbn, "Precisa ter um ISBN para criar um novo livro");
        this.titulo = titulo;
        this.preco = preco;
        this.isbn = isbn;
    }

    @Override
    public Livro toLivro() {
        return new Livro(titulo, preco, isbn);
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
