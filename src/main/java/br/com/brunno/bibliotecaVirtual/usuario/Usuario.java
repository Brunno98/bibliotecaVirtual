package br.com.brunno.bibliotecaVirtual.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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
}
