package br.com.brunno.bibliotecaVirtual.usuario;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoUsuarioController {

    private final CriaNovoUsuario criaNovoUsuario;

    public NovoUsuarioController(CriaNovoUsuario criaNovoUsuario) {
        this.criaNovoUsuario = criaNovoUsuario;
    }

    @PostMapping("/usuario")
    public NovoUsuarioResponse criaNovoUsuario(@RequestBody @Valid NovoUsuarioRequest novoUsuarioRequest) {
        Usuario usuario = criaNovoUsuario.executa(novoUsuarioRequest);
        return new NovoUsuarioResponse(usuario);
    }

}
