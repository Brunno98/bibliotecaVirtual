package br.com.brunno.bibliotecaVirtual.livro;

import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private BigDecimal preco;

    private String isbn;

    @OneToMany(mappedBy = "livro")
    private List<Exemplar> exemplares = new ArrayList<>();

    @Deprecated
    public Livro() {}

    public Livro(String titulo, BigDecimal preco, String isbn) {
        Assert.hasText(titulo, "Livro precisa ter um titulo");
        Assert.notNull(preco, "Livro precisa ter um preco");
        Assert.hasText(isbn, "Livro precisa ter um codigo ISBN");
        this.titulo = titulo;
        this.preco = preco;
        this.isbn = isbn;
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

    public Optional<Exemplar> buscaExemplarDisponivel(Usuario usuario) {
        return this.exemplares.stream()
                .filter(Exemplar::disponivelParaEmprestimo)
                .filter(exemplar -> exemplar.aceita(usuario))
                .findFirst();
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", preco=" + preco +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
