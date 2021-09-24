package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Classe que representa Faturamento
 *
 * @author Thiago Ferreira <thiago.af19@gmail.com>, Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.2
 * @since 1.0.0
 */

public class Faturamento extends AbstractEntity {
    private Convenio convenio;
    private Consultorio consultorio;
    private Boolean finalizado;
    private Integer numero;
    @JsonProperty("datavencimento")
    private Date dataVencimento;
    private Date inicio;
    private Date fim;
    private Date createdAt;
    private double valor;

    public Faturamento() {
    }

    public Faturamento(int id) {
        this.setId(id);
    }

    private Faturamento(Builder builder) {
        id = builder.id;
        convenio = builder.convenio;
        consultorio = builder.consultorio;
        finalizado = builder.finalizado;
        numero = builder.numero;
        dataVencimento = builder.dataVencimento;
        inicio = builder.inicio;
        fim = builder.fim;
        createdAt = builder.createdAt;
        valor = builder.valor;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public Integer getNumero() {
        return numero;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getInicio() {
        return inicio;
    }

    public Date getFim() {
        return fim;
    }

    public static final class Builder {

        private Integer id;
        private Convenio convenio;
        private Consultorio consultorio;
        private Boolean finalizado;
        private Integer numero;
        private Date dataVencimento;
        private Date inicio;
        private Date fim;
        private Date createdAt;
        private double valor;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder convenio(Convenio val) {
            convenio = val;
            return this;
        }
        public Builder valor(double val) {
            valor = val;
            return this;
        }
        public Builder consultorio(Consultorio val) {
            consultorio = val;
            return this;
        }

        public Builder finalizado(Boolean val) {
            finalizado = val;
            return this;
        }

        public Builder numero(Integer val) {
            numero = val;
            return this;
        }

        public Builder dataVencimento(Date val) {
            dataVencimento = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder inicio(Date inicio) {
            this.inicio = inicio;
            return this;
        }

        public Builder fim(Date fim) {
            this.fim = fim;
            return this;
        }

        public Faturamento build() {
            return new Faturamento(this);
        }
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Faturamento{" +
                "id=" + id +
                ", convenio=" + convenio +
                ", datavencimento=" + dataVencimento +
                ", finalizado='" + finalizado + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}