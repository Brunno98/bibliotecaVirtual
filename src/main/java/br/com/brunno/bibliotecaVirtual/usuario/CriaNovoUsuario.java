package br.com.brunno.bibliotecaVirtual.usuario;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CriaNovoUsuario {

    private final UsuarioRepository usuarioRepository;

    public CriaNovoUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario executa(DadosNovoUsuario dadosNovoUsuario) {
        Usuario usuario = dadosNovoUsuario.toUsuario();
        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        possivelUsuario.ifPresent(existente -> {throw new IllegalArgumentException("Usuario já existente");});

        return usuarioRepository.save(usuario);
    }
}
