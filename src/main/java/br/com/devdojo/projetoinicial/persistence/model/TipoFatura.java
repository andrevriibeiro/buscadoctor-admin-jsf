package br.com.devdojo.projetoinicial.persistence.model;

/**
 * Classe que representa o tipo da fatura
 *
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.2
 * @since 1.0.0
 */
public class TipoFatura extends AbstractTipo {

    public TipoFatura() {
    }

    private TipoFatura(Builder builder) {
        id = builder.id;
        tipo = builder.tipo;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    public static Builder newBuilder() {
        return new Builder();
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

        public TipoFatura build() {
            return new TipoFatura(this);
        }
    }
}