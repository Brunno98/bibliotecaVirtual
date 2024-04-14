package br.com.brunno.bibliotecaVirtual.usuario;

import br.com.brunno.bibliotecaVirtual.livro.Emprestimo;
import br.com.brunno.bibliotecaVirtual.livro.Livro;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    public enum Tipo {
        PADRAO,
        PESQUISADOR;
    }

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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
