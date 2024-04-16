package br.com.brunno.bibliotecaVirtual.emprestimo;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NovoEmprestimoResquest {

    private Integer diasDeEmprestimo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovoEmprestimoResquest(Integer diasDeEmprestimo) {
        this.diasDeEmprestimo = diasDeEmprestimo;
    }

    public Integer getDiasDeEmprestimo() {
        return diasDeEmprestimo;
    }
}
