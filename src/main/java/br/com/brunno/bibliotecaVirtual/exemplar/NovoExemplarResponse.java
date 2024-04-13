package br.com.brunno.bibliotecaVirtual.exemplar;

public class NovoExemplarResponse {

    public final Long id;
    public final String isbn;
    public final String tipo;

    public NovoExemplarResponse(Exemplar exemplar) {
        this.id = exemplar.getId();
        this.isbn = exemplar.getIsbn();
        this.tipo = exemplar.getTipo().toString();

    }
}
