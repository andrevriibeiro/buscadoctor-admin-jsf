package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class Origem extends AbstractEntity {
    private String origem;

    public Origem() {
    }

    private Origem(Builder builder) {
        id = builder.id;
        origem = builder.origem;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public static final class Builder {

        private Integer id;
        private String origem;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder origem(String origem) {
            this.origem = origem;
            return this;
        }

        public Origem build() {
            return new Origem(this);
        }
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "no";
    }
}
