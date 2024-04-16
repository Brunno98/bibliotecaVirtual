package br.com.brunno.bibliotecaVirtual.usuario;

import br.com.brunno.bibliotecaVirtual.compartilhado.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class NovoUsuarioRequest implements DadosNovoUsuario {

    @Email
    @NotBlank
    @Unique(domain = Usuario.class, fieldName = "email")
    private String email;
    @NotBlank
    @Length(min = 6)
    private String senha;
    @NotNull
    private Tipo tipo;

    public NovoUsuarioRequest(String email, String senha, Tipo tipo) {
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }


    @Override
    public Usuario toUsuario() {
        return new Usuario(email, tipo, new SenhaLimpa(senha));
    }
}
