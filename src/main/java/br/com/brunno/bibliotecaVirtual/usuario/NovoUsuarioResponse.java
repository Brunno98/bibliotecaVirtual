package br.com.brunno.bibliotecaVirtual.usuario;

public class NovoUsuarioResponse {

    private final String email;
    private final Usuario.Tipo tipo;

    public NovoUsuarioResponse(Usuario usuario) {
        this.email = usuario.getEmail();
        this.tipo = usuario.getTipo();
    }

    public String getEmail() {
        return email;
    }

    public Usuario.Tipo getTipo() {
        return tipo;
    }
}
