package br.com.brunno.bibliotecaVirtual.usuario;

import br.com.brunno.bibliotecaVirtual.compartilhado.ExcludeGeneratedFromJaCoCo;
import br.com.brunno.bibliotecaVirtual.exemplar.Exemplar;
import br.com.brunno.bibliotecaVirtual.emprestimo.Emprestimo;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private Tipo tipo;
    private String senha;
    @OneToMany(mappedBy = "usuario")
    private List<Emprestimo> emprestimos = new ArrayList<>();

    @Deprecated
    public Usuario() {}

    public Usuario(String email, Tipo tipo, SenhaLimpa senha) {
        this.email = email;
        this.tipo = tipo;
        this.senha = senha.getHash();
    }

    public String getEmail() {
        return email;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int quantidadeDeEmprestimo() {
        return this.emprestimos.size();
    }

    public boolean is(Tipo tipo) {
        return this.tipo.equals(tipo);
    }

    public Emprestimo novoEmprestimo(Livro livro, Integer prazoDeEmprestimo) {
        Assert.isTrue(this.tipo.aceitaNovoEmprestimo(this), "Usuario nao é capaz de pegar um novo emprestimo");
        Assert.isTrue(this.prazoDeEmprestimoValido(prazoDeEmprestimo), "Não deveria pedir um novo emprestimo com prazo invalido. Prazo: "+prazoDeEmprestimo);

        Optional<Exemplar> possivelExemplar = livro.buscaExemplarDisponivel(this);
        Assert.isTrue(possivelExemplar.isPresent(), "Livro precisa ter um exemplar disponivel para o usuario");
        Exemplar exemplar = possivelExemplar.get();

        Emprestimo emprestimo = exemplar.criaEmprestimo(this, prazoDeEmprestimo);
        this.emprestimos.add(emprestimo);
        return emprestimo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                '}';
    }

    public boolean prazoDeEmprestimoValido(Integer prazoDeEmprestimo) {
        return this.tipo.prazoDeEmprestimoValido(prazoDeEmprestimo);
    }

    public boolean temEmprestimoExpirado() {
        return this.emprestimos.stream().anyMatch(Emprestimo::expirado);
    }

    @ExcludeGeneratedFromJaCoCo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email) && tipo == usuario.tipo;
    }

    @ExcludeGeneratedFromJaCoCo
    @Override
    public int hashCode() {
        return Objects.hash(email, tipo);
    }
}
