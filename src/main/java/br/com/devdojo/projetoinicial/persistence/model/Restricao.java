package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Esta classe representa o Restricao, contem suas caracteristicas e acoes.
 * <p>
 * Utilizar somente nas operacoes especificas.
 *
 * @author <a href="mailto:gmendes92@gmail.com">Guilherme Mendes</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class Restricao extends AbstractEntity {
    @JsonProperty("datainicio")
    private Date dataInicio;
    @JsonProperty("datafinal")
    private Date dataFinal;
    private String restricao;
    @JsonProperty("tiporestricao")
    private TipoRestricao tipoRestricao;

    private Date createdAt;

    public Restricao() {
    }

    private Restricao(Builder builder) {
        id = builder.id;
        dataInicio = builder.dataInicio;
        dataFinal = builder.dataFinal;
        restricao = builder.restricao;
        tipoRestricao = builder.tipoRestricao;
        createdAt = builder.createdAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public String getRestricao() {
        return restricao;
    }

    public TipoRestricao getTipoRestricao() {
        return tipoRestricao;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Restricao{" +
                "dataInicio=" + dataInicio +
                ", dataFinal=" + dataFinal +
                ", restricao='" + restricao + '\'' +
                ", tipoRestricao=" + tipoRestricao +
                ", createdAt=" + createdAt +
                '}';
    }

    public static final class Builder {
        private Integer id;
        private Date dataInicio;
        private Date dataFinal;
        private String restricao;
        private TipoRestricao tipoRestricao;
        private Date createdAt;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder dataInicio(Date val) {
            dataInicio = val;
            return this;
        }

        public Builder dataFinal(Date val) {
            dataFinal = val;
            return this;
        }

        public Builder restricao(String val) {
            restricao = val;
            return this;
        }

        public Builder tipoRestricao(TipoRestricao val) {
            tipoRestricao = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Restricao build() {
            return new Restricao(this);
        }
    }
}