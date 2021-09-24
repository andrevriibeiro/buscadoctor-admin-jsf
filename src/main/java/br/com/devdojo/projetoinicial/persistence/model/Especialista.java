package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.1
 * @since 1.0.0
 */
public class Especialista extends AbstractEntity {

    private String nome;
    @JsonProperty("razaosocial")
    private String razaoSocial;
    private String cnpj;
    private String cpf;
    private String rg;
    private String crm;
    private Double rating;
    private String sexo;
    private String foto;
    private String usuario;
    private String senha;
    private String email;
    private Integer numero;
    private String celular;
    @JsonProperty("telefoneresidencia")
    private String telefoneResidencia;
    @JsonProperty("telefonerecado")
    private String telefoneRecado;
    private Date createdAt;
    private List<Especialidade> especialidade;

    private ContaBancaria contaBancaria;

    private Logradouro logradouro;

    private Set<Permissao> permissao;

    private Set<Convenio> convenio;

    private Consultorio consultorio;

    public Especialista() {
    }

    public Especialista(Integer id) {
        this.id = id;
    }

    private Especialista(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.razaoSocial = builder.razaoSocial;
        this.cnpj = builder.cnpj;
        this.cpf = builder.cpf;
        this.rg = builder.rg;
        this.crm = builder.crm;
        this.rating = builder.rating;
        this.sexo = builder.sexo;
        this.foto = builder.foto;
        this.usuario = builder.usuario;
        this.senha = builder.senha;
        this.email = builder.email;
        this.numero = builder.numero;
        this.celular = builder.celular;
        this.telefoneResidencia = builder.telefoneResidencia;
        this.telefoneRecado = builder.telefoneRecado;
        this.createdAt = builder.createdAt;
        this.contaBancaria = builder.contaBancaria;
        this.logradouro = builder.logradouro;
        this.permissao = builder.permissao;
        this.convenio = builder.convenio;
        this.consultorio = builder.consultorio;
        this.especialidade = builder.especialidade;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newEspecialista() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public String getCrm() {
        return crm;
    }

    public List<Especialidade> getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(List<Especialidade> especialidade) {
        this.especialidade = especialidade;
    }

    public Double getRating() {
        return rating;
    }

    public String getSexo() {
        return sexo;
    }

    public String getFoto() {
        return foto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setTelefoneResidencia(String telefoneResidencia) {
        this.telefoneResidencia = telefoneResidencia;
    }

    public void setTelefoneRecado(String telefoneRecado) {
        this.telefoneRecado = telefoneRecado;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public void setPermissao(Set<Permissao> permissao) {
        this.permissao = permissao;
    }

    public void setConvenio(Set<Convenio> convenio) {
        this.convenio = convenio;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public String getEmail() {
        return email;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getCelular() {
        return celular;
    }

    public String getTelefoneResidencia() {
        return telefoneResidencia;
    }

    public String getTelefoneRecado() {
        return telefoneRecado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public Set<Permissao> getPermissao() {
        return permissao;
    }

    public Set<Convenio> getConvenio() {
        return convenio;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    @Override
    public String toString() {
        return "Especialista{" +
                "nome='" + nome + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", crm='" + crm + '\'' +
                ", rating=" + rating +
                ", sexo='" + sexo + '\'' +
                ", foto='" + foto + '\'' +
                ", usuario='" + usuario + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", numero=" + numero +
                ", celular='" + celular + '\'' +
                ", telefoneResidencia='" + telefoneResidencia + '\'' +
                ", telefoneRecado='" + telefoneRecado + '\'' +
                ", createdAt=" + createdAt +
                ", contaBancaria=" + contaBancaria +
                ", logradouro=" + logradouro +
                ", permissao=" + permissao +
                ", convenio=" + convenio +
                ", consultorio=" + consultorio +
                "} ";
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private String razaoSocial;
        private String cnpj;
        private String cpf;
        private String rg;
        private String crm;
        private Double rating;
        private String sexo;
        private String foto;
        private String usuario;
        private String senha;
        private String email;
        private Integer numero;
        private String celular;
        private String telefoneResidencia;
        private String telefoneRecado;
        private Date createdAt;
        private ContaBancaria contaBancaria;
        private Logradouro logradouro;
        private Set<Permissao> permissao;
        private Set<Convenio> convenio;
        private Consultorio consultorio;
        private List<Especialidade> especialidade;

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

        public Builder cnpj(String val) {
            cnpj = val;
            return this;
        }

        public Builder cpf(String val) {
            cpf = val;
            return this;
        }

        public Builder rg(String val) {
            rg = val;
            return this;
        }

        public Builder crm(String val) {
            crm = val;
            return this;
        }

        public Builder rating(Double val) {
            rating = val;
            return this;
        }

        public Builder sexo(String val) {
            sexo = val;
            return this;
        }

        public Builder foto(String val) {
            foto = val;
            return this;
        }

        public Builder usuario(String val) {
            usuario = val;
            return this;
        }

        public Builder senha(String val) {
            senha = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder numero(Integer val) {
            numero = val;
            return this;
        }

        public Builder celular(String val) {
            celular = val;
            return this;
        }

        public Builder telefoneresidencia(String val) {
            telefoneResidencia = val;
            return this;
        }

        public Builder telefonerecado(String val) {
            telefoneRecado = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder contaBancaria(ContaBancaria val) {
            contaBancaria = val;
            return this;
        }

        public Builder logradouro(Logradouro val) {
            logradouro = val;
            return this;
        }

        public Builder permissao(Set<Permissao> val) {
            permissao = val;
            return this;
        }

        public Builder convenio(Set<Convenio> val) {
            convenio = val;
            return this;
        }

        public Builder consultorio(Consultorio val) {
            consultorio = val;
            return this;
        }

        public Especialista build() {
            return new Especialista(this);
        }

        public Builder especialidade(List<Especialidade> especialidade) {
            this.especialidade = especialidade;
            return this;
        }
    }
}
