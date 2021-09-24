package br.com.devdojo.projetoinicial.persistence.model;

/**
 * Esta classe representa a Cidade, contem suas caracteristicas e acoes.
 * <p>
 * Utilizar somente nas operacoes especificas.
 *
 * @author Thiago Ferreira <thiago.af19@gmail.com>, Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class Banco extends AbstractEntity {

    private Integer codigo;
    private String nome;

    public Banco() {
    }

    private Banco(Builder builder) {
        id = builder.id;
        codigo = builder.codigo;
        nome = builder.nome;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static final class Builder {

        private Integer id;
        private Integer codigo;
        private String nome;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder codigo(Integer val) {
            codigo = val;
            return this;
        }

        public Builder nome(String val) {
            nome = val;
            return this;
        }

        public Banco build() {
            return new Banco(this);
        }
    }

    @Override
    public String toString() {
        return "Banco{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                "} ";
    }
}
