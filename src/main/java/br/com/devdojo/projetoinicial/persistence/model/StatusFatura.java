package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class StatusFatura extends AbstractEntity {

    private String status;

    public StatusFatura() {
    }

    private StatusFatura(Builder builder) {
        id = builder.id;
        status = builder.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getStatus() {
        return status;
    }

    public static final class Builder {

        private Integer id;
        private String status;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public StatusFatura build() {
            return new StatusFatura(this);
        }
    }
}