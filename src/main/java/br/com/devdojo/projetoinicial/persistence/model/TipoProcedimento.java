package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class TipoProcedimento extends AbstractEntity {

    private String tipo;

    public TipoProcedimento() {
    }

    private TipoProcedimento(Builder builder) {
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

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public TipoProcedimento build() {
            return new TipoProcedimento(this);
        }
    }
}
