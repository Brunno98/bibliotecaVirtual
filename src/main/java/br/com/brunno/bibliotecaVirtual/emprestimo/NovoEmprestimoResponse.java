package br.com.brunno.bibliotecaVirtual.emprestimo;

import java.time.LocalDate;

public class NovoEmprestimoResponse {

    public final Long id;
    public final LocalDate prazoDeEmprestimo;

    public NovoEmprestimoResponse(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.prazoDeEmprestimo = emprestimo.getPrazoDeEmprestimo();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPrazoDeEmprestimo() {
        return prazoDeEmprestimo;
    }
}
