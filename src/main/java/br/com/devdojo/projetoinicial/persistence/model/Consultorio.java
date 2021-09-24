package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Set;

public class Consultorio extends AbstractEntity {



    private String nome;
    private String telefone;
    private Integer numero;
    private String complemento;
    private Double rating;
    private String banner;
    private Boolean marketing;
    private Integer limiteEspecialistas;
    private String email;
    private Integer prioridade;
    private String cnpj;
    private String cnes;
    @JsonProperty("razaosocial")
    private String razaoSocial;
    private Date createdAt;

    private Logradouro logradouro;
    private Tipo tipo;

    private Set<ConsultorioConvenio> consultorioConvenios;

    private Set<Especialista> especialistas;

    public Consultorio() {
    }

    private Consultorio(Builder builder) {
        setId(builder.id);
        nome = builder.nome;
        telefone = builder.telefone;
        numero = builder.numero;
        complemento = builder.complemento;
        rating = builder.rating;
        banner = builder.banner;
        marketing = builder.marketing;
        limiteEspecialistas = builder.limiteEspecialistas;
        email = builder.email;
        prioridade = builder.prioridade;
        cnpj = builder.cnpj;
        cnes = builder.cnes;
        razaoSocial = builder.razaoSocial;
        createdAt = builder.createdAt;
        logradouro = builder.logradouro;
        tipo = builder.tipo;
        consultorioConvenios = builder.consultorioConvenios;
        especialistas = builder.especialistas;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Double getRating() {
        return rating;
    }

    public String getBanner() {
        return banner;
    }

    public Boolean getMarketing() {
        return marketing;
    }

    public Integer getLimiteEspecialistas() {
        return limiteEspecialistas;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCnes() {
        return cnes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Set<ConsultorioConvenio> getConsultorioConvenios() {
        return consultorioConvenios;
    }

    public Set<Especialista> getEspecialistas() {
        return especialistas;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setMarketing(Boolean marketing) {
        this.marketing = marketing;
    }

    public void setLimiteEspecialistas(Integer limiteEspecialistas) {
        this.limiteEspecialistas = limiteEspecialistas;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setConsultorioConvenios(Set<ConsultorioConvenio> consultorioConvenios) {
        this.consultorioConvenios = consultorioConvenios;
    }

    public void setEspecialistas(Set<Especialista> especialistas) {
        this.especialistas = especialistas;
    }

    public static final class Builder {
        private Integer id;
        private String nome;
        private String telefone;
        private Integer numero;
        private String complemento;
        private Double rating;
        private String banner;
        private Boolean marketing;
        private Integer limiteEspecialistas;
        private String email;
        private Integer prioridade;
        private String cnpj;
        private String cnes;
        private String razaoSocial;
        private Date createdAt;
        private Logradouro logradouro;
        private Tipo tipo;
        private Set<ConsultorioConvenio> consultorioConvenios;
        private Set<Especialista> especialistas;

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

        public Builder telefone(String val) {
            telefone = val;
            return this;
        }

        public Builder numero(Integer val) {
            numero = val;
            return this;
        }

        public Builder complemento(String val) {
            complemento = val;
            return this;
        }

        public Builder rating(Double val) {
            rating = val;
            return this;
        }

        public Builder banner(String val) {
            banner = val;
            return this;
        }

        public Builder marketing(Boolean val) {
            marketing = val;
            return this;
        }

        public Builder limiteEspecialistas(Integer val) {
            limiteEspecialistas = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder prioridade(Integer val) {
            prioridade = val;
            return this;
        }

        public Builder cnpj(String val) {
            cnpj = val;
            return this;
        }

        public Builder cnes(String val) {
            cnes = val;
            return this;
        }

        public Builder razaosocial(String val) {
            razaoSocial = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder logradouro(Logradouro val) {
            logradouro = val;
            return this;
        }

        public Builder tipo(Tipo val) {
            tipo = val;
            return this;
        }

        public Builder consultorioConvenios(Set<ConsultorioConvenio> val) {
            consultorioConvenios = val;
            return this;
        }

        public Builder especialistas(Set<Especialista> val) {
            especialistas = val;
            return this;
        }

        public Consultorio build() {
            return new Consultorio(this);
        }
    }
}
