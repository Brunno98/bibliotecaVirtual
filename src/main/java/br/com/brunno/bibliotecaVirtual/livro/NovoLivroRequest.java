package br.com.brunno.bibliotecaVirtual.livro;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class NovoLivroRequest implements DadosNovoLivro {

    @NotBlank
    private String titulo;
    @NotNull
    @DecimalMin("0")
    private BigDecimal preco;
    @NotBlank
    private String isbn;

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
