package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContaBancaria extends AbstractEntity {

    private Banco banco;
    @JsonProperty("contacorrente")
    private String contaCorrente;
    private String agencia;
    private String observacao;

    public ContaBancaria() {
    }

    public ContaBancaria(Integer id) {
        this.id = id;
    }

    private ContaBancaria(Builder builder) {
        this.id = builder.id;
        this.banco = builder.banco;
        this.contaCorrente = builder.contaCorrente;
        this.agencia = builder.agencia;
        this.observacao = builder.observacao;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newContaBancaria() {
        return new Builder();
    }

    public Banco getBanco() {
        return banco;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getObservacao() {
        return observacao;
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
                "id='" + id + '\'' +
                ", contaCorrente='" + contaCorrente + '\'' +
                ", agencia='" + agencia + '\'' +
                ", observacao='" + observacao + '\'' +
                ", banco='" + banco + '\'' +
                "} ";
    }

    public static final class Builder {
        private Integer id;
        private Banco banco;
        private String contaCorrente;
        private String agencia;
        private String observacao;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder banco(Banco val) {
            banco = val;
            return this;
        }

        public Builder contacorrente(String val) {
            contaCorrente = val;
            return this;
        }

        public Builder agencia(String val) {
            agencia = val;
            return this;
        }

        public Builder observacao(String val) {
            observacao = val;
            return this;
        }

        public ContaBancaria build() {
            return new ContaBancaria(this);
        }
    }
}