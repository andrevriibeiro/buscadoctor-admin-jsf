package br.com.devdojo.projetoinicial.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Esta classe representa o TipoRestricao, contem suas caracteristicas e acoes.
 * <p>
 * Utilizar somente nas operacoes especificas.
 *
 * @author Thiago Ferreira <thiago.af19@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class TipoRestricao extends AbstractTipo {

    private String tipo;

    public TipoRestricao() {
    }

    private TipoRestricao(Builder builder) {
        id = builder.id;
        tipo = builder.tipo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTipo() {
        return tipo;
    }

    public static final class Builder {

        private Integer id;
        private String tipo;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder tipo(String val) {
            tipo = val;
            return this;
        }

        public TipoRestricao build() {
            return new TipoRestricao(this);
        }
    }
}