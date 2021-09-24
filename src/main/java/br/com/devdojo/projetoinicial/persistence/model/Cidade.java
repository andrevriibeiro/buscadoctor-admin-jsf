package br.com.devdojo.projetoinicial.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Esta classe representa a Cidade, contem suas caracteristicas e acoes.
 * <p>
 * Utilizar somente nas operacoes especificas.
 *
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class Cidade extends AbstractEntity {

    private String nome;

    private Estado estado;

    public Cidade() {
    }

    public Cidade(int id) {
        this.setId(id);
    }

    private Cidade(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        estado = builder.estado;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Estado getEstado() { return estado; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private Estado estado;

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

        public Builder estado(Estado val) {
            estado = val;
            return this;
        }

        public Cidade build() {
            return new Cidade(this);
        }
    }

    @Override
    public String toString() {
        return "Cidade{" +
                "nome='" + nome + '\'' +
                ", estado='" + estado + '\'' +
                "} ";
    }
}
