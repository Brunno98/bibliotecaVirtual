package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.livro.Livro;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    private Tipo tipo;

    public Exemplar(Livro livro, Tipo tipo) {
        Assert.notNull(tipo, "Exemplar precisa ter um tipo");
        Assert.notNull(livro, "Exemplar precisar ter um livro existente");
        this.livro = livro;
        this.tipo = tipo;
    }

    public Long getId() {
        Assert.notNull(id, "Exemplar ainda nao foi salvo em banco, então nao tem id");
        return this.id;
    }

    public String getIsbn() {
        Assert.state(Objects.nonNull(livro), "Exemplar deveria ter um livro associado");
        return this.livro.getIsbn();
    }

    public Tipo getTipo() {
        return tipo;
    }

    public enum Tipo {
        LIVRE,
        RESTRITO;
    }

}
