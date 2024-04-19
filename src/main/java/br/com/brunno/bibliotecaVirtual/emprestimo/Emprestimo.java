package br.com.brunno.bibliotecaVirtual.emprestimo;

import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
import br.com.brunno.bibliotecaVirtual.usuario.Usuario;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Exemplar exemplar;

    private LocalDate prazoDeEmprestimo;

    @Nullable
    private LocalDate dataDevolucao;


    @Deprecated
    public Emprestimo() {}

    public Emprestimo(Usuario usuario, Exemplar exemplar, Integer diasDeEmprestimo) {
        Assert.notNull(usuario, "Emprestimo precisa ter um usuario");
        Assert.notNull(exemplar, "Emprestimo pracisa ter um exemplar");
        Assert.notNull(diasDeEmprestimo, "Precisa ser defino os dias de prazo de emprestimo");
        Assert.isTrue(diasDeEmprestimo > 0, "Nao pode criar emprestimo com dias negativo ou zerado");
        this.usuario = usuario;
        this.exemplar = exemplar;
        this.prazoDeEmprestimo = LocalDate.now().plusDays(diasDeEmprestimo);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", exemplar=" + exemplar +
                ", prazoDeEmprestimo=" + prazoDeEmprestimo +
                '}';
    }

    public boolean devolvido() {
        return Objects.nonNull(this.dataDevolucao);
    }

    public boolean expirado() {
        if (Objects.nonNull(this.dataDevolucao)) return false;
        return LocalDate.now().isAfter(prazoDeEmprestimo);
    }
}
