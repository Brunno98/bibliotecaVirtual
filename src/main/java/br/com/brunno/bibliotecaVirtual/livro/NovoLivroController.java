package br.com.brunno.bibliotecaVirtual.livro;

import jakarta.validation.Valid;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoLivroController {

    private final LivroUnicoWebValidator livroUnicoWebValidator;
    private final CriaNovoLivro criaNovoLivro;

    public NovoLivroController(LivroUnicoWebValidator livroUnicoWebValidator, CriaNovoLivro criaNovoLivro) {
        this.criaNovoLivro = criaNovoLivro;
        this.livroUnicoWebValidator = livroUnicoWebValidator;
    }

    @InitBinder
    public void bind(WebDataBinder binder) {
        binder.addValidators(livroUnicoWebValidator);
    }

    @PostMapping("/livro")
    public CreateLivroResponse criaNovoLivro(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        Livro livro = criaNovoLivro.executa(novoLivroRequest);

        return new CreateLivroResponse(livro);
    }
}
