package br.com.brunno.bibliotecaVirtual.usuario;

import java.util.Objects;

public enum Tipo {
    PADRAO {
        @Override
        public boolean prazoDeEmprestimoValido(Integer prazoDeEmprestimo) {
            if (Objects.isNull(prazoDeEmprestimo)) return false;
            return prazoDeEmprestimo > 0 && prazoDeEmprestimo <= 60;
        }

        @Override
        public boolean aceitaNovoEmprestimo(Usuario usuario) {
            return usuario.quantidadeDeEmprestimo() < 5;
        }
    },
    PESQUISADOR {
        @Override
        public boolean prazoDeEmprestimoValido(Integer prazoDeEmprestimo) {
            if (Objects.isNull(prazoDeEmprestimo)) return true;
            return prazoDeEmprestimo > 0 && prazoDeEmprestimo <= 60;
        }

        @Override
        public boolean aceitaNovoEmprestimo(Usuario usuario) {
            return true;
        }
    };

    public abstract boolean prazoDeEmprestimoValido(Integer prazoDeEmprestimo);

    public abstract boolean aceitaNovoEmprestimo(Usuario usuario);
}
