package br.com.brunno.bibliotecaVirtual.usuario;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaLimpa {

    private final String senha;

    public SenhaLimpa(String senha) {
        this.senha = senha;
    }

    public String getHash() {
        return new BCryptPasswordEncoder().encode(senha);
    }
}
