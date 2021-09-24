package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Convenio extends AbstractEntity {

    private String nome;
    @JsonProperty("razaosocial")
    private String razaoSocial;
    private String codANS;
    private String cnpj;

    public Convenio() {
    }

    private Convenio(Builder builder) {
        setId(builder.id);
        nome = builder.nome;
        razaoSocial = builder.razaoSocial;
        codANS = builder.codANS;
        cnpj = builder.cnpj;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCodANS() {
        return codANS;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static final class Builder {
        private Integer id;
        private String nome;
        private String razaoSocial;
        private String codANS;
        private String cnpj;

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

        public Builder razaosocial(String val) {
            razaoSocial = val;
            return this;
        }

        public Builder codANS(String val) {
            codANS = val;
            return this;
        }

        public Builder cnpj(String val) {
            cnpj = val;
            return this;
        }

        public Convenio build() {
            return new Convenio(this);
        }
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setCodANS(String codANS) {
        this.codANS = codANS;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "Convenio{" +
                "nome='" + nome + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", codANS='" + codANS + '\'' +
                ", id='" + id + '\'' +
                ", cnpj='" + cnpj + '\'' +
                "} ";
    }
}
