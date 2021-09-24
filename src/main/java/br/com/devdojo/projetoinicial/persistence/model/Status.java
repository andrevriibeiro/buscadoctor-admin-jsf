package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @see 1.1.0
 * @since 1.0.0
 */
public class Status extends AbstractEntity {

    private String nome;

    public Status() {
    }

    public Status(Integer id) {
        this.id = id;
    }

    public Status(Builder builder) {
        id = builder.id;
        nome = builder.nome;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static final class Builder {

        private Integer id;
        private String nome;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Status build() {
            return new Status(this);
        }
    }

    @Override
    public String toString() {
        return "Status{" +
                "nome='" + nome +
                "} " + super.toString();
    }
}
