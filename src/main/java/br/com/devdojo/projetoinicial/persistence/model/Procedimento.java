package br.com.devdojo.projetoinicial.persistence.model;

import java.util.Date;

/**
 * @version 1.1.0
 * @since 1.0.0
 */
public class Procedimento extends AbstractEntity {

    private String nome;
    private Integer codANS;
    private TipoProcedimento tipo;
    private Date createdAt;

    public Procedimento() {
    }

    private Procedimento(Builder builder) {
        setId(builder.id);
        nome = builder.nome;
        codANS = builder.codANS;
        tipo = builder.tipo;
        createdAt = builder.createdAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public Integer getCodANS() {
        return codANS;
    }

    public TipoProcedimento getTipo() {
        return tipo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Procedimento{" +
                "nome='" + nome + '\'' +
                ", codANS=" + codANS +
                ", tipo=" + tipo +
                ", createdAt=" + createdAt +
                "} " + super.toString();
    }

    public static final class Builder {
        private Integer id;
        private String nome;
        private Integer codANS;
        private TipoProcedimento tipo;
        private Date createdAt;

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

        public Builder codANS(Integer val) {
            codANS = val;
            return this;
        }

        public Builder tipo(TipoProcedimento val) {
            tipo = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Procedimento build() {
            return new Procedimento(this);
        }
    }
}