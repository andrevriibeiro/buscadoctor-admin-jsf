package br.com.devdojo.projetoinicial.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Esta classe representa o Estado, contem suas caracteristicas e acoes.
 * <p>
 * Utilizar somente nas operacoes especificas.
 *
 * @author Thiago Ferreira <thiago.af19@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class Estado extends AbstractEntity {

    private String nome;
    private String acronimo;

    public Estado() {
    }

    public Estado(int id) {
        this.setId(id);
    }

    private Estado(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        acronimo = builder.acronimo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private String acronimo;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder nome(String val) {
            nome = val;
            return this;
        }

        public Builder acronimo(String val) {
            acronimo = val;
            return this;
        }

        public Estado build() {
            return new Estado(this);
        }
    }

    @Override
    public String toString() {
        return "Estado{" +
                "nome='" + nome + '\'' +
                "acronimo='" + acronimo + '\'' +
                "} ";
    }
}
