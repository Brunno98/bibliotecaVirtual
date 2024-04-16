package br.com.brunno.bibliotecaVirtual.exemplar;

import br.com.brunno.bibliotecaVirtual.usuario.Usuario;

public enum Tipo {
    LIVRE {
        @Override
        public boolean aceita(Usuario usuario) {
            return true;
        }
    },
    RESTRITO {
        @Override
        public boolean aceita(Usuario usuario) {
            return usuario.is(br.com.brunno.bibliotecaVirtual.usuario.Tipo.PESQUISADOR);
        }
    };

    public abstract boolean aceita(Usuario usuario);
}
