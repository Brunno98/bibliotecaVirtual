package br.com.brunno.bibliotecaVirtual.livro;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LivroRepository extends CrudRepository<Livro, Long> {
    Optional<Livro> findByTituloOrIsbn(String titulo, String isbn);
}
