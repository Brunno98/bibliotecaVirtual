package br.com.brunno.bibliotecaVirtual.livro;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LivroUnicoWebValidator implements Validator {

    private final LivroUnicoValidator livroUnicoValidator;

    public LivroUnicoWebValidator(LivroUnicoValidator livroUnicoValidator) {
        this.livroUnicoValidator = livroUnicoValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NovoLivroRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;
        NovoLivroRequest request = (NovoLivroRequest) target;

        livroUnicoValidator.validate(request,
                () -> errors.reject(null, "Livro já existe com esse titulo ou ISBN"));
    }
}
