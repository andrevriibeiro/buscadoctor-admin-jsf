package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>, Guilherme Mendes<gmendes92@gmail.com>
 * @version 1.1.1
 * @since 1.0.0
 */
public class Usuario extends AbstractEntity {

    private String nome;
        private String email;
        private String user;
        private String senha;
        private String sexo;
        private String celular;
        private String foto;
        private Date birthday;
        private Boolean acesso;
        private Integer numero;
        private String complemento;
        private String rg;
        private String cpf;
        @JsonProperty("telefoneresidencia")
        private String telefoneResidencia;
        @JsonProperty("telefonerecado")
        private String telefoneRecado;
        private String profissao;
        @JsonProperty("estadocivil")
        private String estadoCivil;
        private String naturalidade;
        private String cor;
        private Configuracao configuracao;
        private Logradouro logradouro;
        private Date createdAt;

    public Usuario() {
    }

    private Usuario(Builder builder) {
        setId(builder.id);
        nome = builder.nome;
        email = builder.email;
        user = builder.user;
        senha = builder.senha;
        sexo = builder.sexo;
        celular = builder.celular;
        foto = builder.foto;
        birthday = builder.birthday;
        createdAt = builder.createdAt;
        acesso = builder.acesso;
        numero = builder.numero;
        complemento = builder.complemento;
        rg = builder.rg;
        cpf = builder.cpf;
        telefoneResidencia = builder.telefoneResidencia;
        telefoneRecado = builder.telefoneRecado;
        profissao = builder.profissao;
        estadoCivil = builder.estadoCivil;
        naturalidade = builder.naturalidade;
        cor = builder.cor;
        configuracao = builder.configuracao;
        logradouro = builder.logradouro;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return user;
    }

    public String getSenha() {
        return senha;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCelular() {
        return celular;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Boolean getAcesso() {
        return acesso;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setAcesso(Boolean acesso) {
        this.acesso = acesso;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefoneResidencia(String telefoneResidencia) {
        this.telefoneResidencia = telefoneResidencia;
    }

    public void setTelefoneRecado(String telefoneRecado) {
        this.telefoneRecado = telefoneRecado;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFoto() {
        return foto;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getRg() {
        return rg;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefoneResidencia() {
        return telefoneResidencia;
    }

    public String getTelefoneRecado() {
        return telefoneRecado;
    }

    public String getProfissao() {
        return profissao;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public String getCor() {
        return cor;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private String email;
        private String user;
        private String senha;
        private String sexo;
        private String celular;
        private String foto;
        private Date birthday;
        private Date createdAt;
        private Boolean acesso;
        private Integer numero;
        private String complemento;
        private String rg;
        private String cpf;
        private String telefoneResidencia;
        private String telefoneRecado;
        private String profissao;
        private String estadoCivil;
        private String naturalidade;
        private String cor;
        private Configuracao configuracao;
        private Logradouro logradouro;

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

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder user(String val) {
            user = val;
            return this;
        }

        public Builder senha(String val) {
            senha = val;
            return this;
        }

        public Builder sexo(String val) {
            sexo = val;
            return this;
        }

        public Builder celular(String val) {
            celular = val;
            return this;
        }

        public Builder foto(String val) {
            foto = val;
            return this;
        }

        public Builder birthday(Date val) {
            birthday = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder acesso(Boolean val) {
            acesso = val;
            return this;
        }

        public Builder numero(Integer numero) {
            this.numero = numero;
            return this;
        }

        public Builder complemento(String complemento) {
            this.complemento = complemento;
            return this;
        }

        public Builder rg(String rg) {
            this.rg = rg;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder telefoneresidencia(String telefoneresidencia) {
            this.telefoneResidencia = telefoneresidencia;
            return this;
        }

        public Builder telefonerecado(String telefonerecado) {
            this.telefoneRecado = telefonerecado;
            return this;
        }

        public Builder profissao(String profissao) {
            this.profissao = profissao;
            return this;
        }

        public Builder estadocivil(String estadocivil) {
            this.estadoCivil = estadocivil;
            return this;
        }

        public Builder naturalidade(String naturalidade) {
            this.naturalidade = naturalidade;
            return this;
        }

        public Builder cor(String cor) {
            this.cor = cor;
            return this;
        }

        public Builder logradouro(Logradouro logradouro) {
            this.logradouro = logradouro;
            return this;
        }

        public Builder configuracao(Configuracao val) {
            configuracao = val;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
