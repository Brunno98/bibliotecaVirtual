package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.emprestimo.Emprestimo;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    private Tipo tipo;

    @OneToMany(mappedBy = "exemplar")
    private List<Emprestimo> emprestimos = new ArrayList<>();


    @Deprecated
    public Exemplar() {}

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

    public boolean aceita(Usuario usuario) {
        return this.tipo.aceita(usuario);
    }

    public Emprestimo criaEmprestimo(Usuario usuario, Integer prazoDeEmprestimo) {
        Assert.isTrue(this.disponivelParaEmprestimo(), "O exemplar precisa estar disponivel para emprestimo");
        Emprestimo emprestimo = new Emprestimo(usuario, this, prazoDeEmprestimo);
        this.emprestimos.add(emprestimo);
        return emprestimo;
    }

    public boolean disponivelParaEmprestimo() {
        return this.emprestimos.stream().allMatch(Emprestimo::foiDevolvido);
    }

    @Override
    public String toString() {
        return "Exemplar{" +
                "id=" + id +
                ", livro=" + livro +
                ", tipo=" + tipo +
                '}';
    }
}
