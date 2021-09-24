package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class Perfil extends AbstractEntity {
    @JsonProperty("nome")
    private String nome;
    private String alias;
    private String user;
    private String senha;

    private String status;
    private Consultorio consultorio;
    private Set<Permissao> permissao;

    public Perfil() {
    }

    private Perfil(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        alias = builder.alias;
        user = builder.user;
        senha = builder.senha;
        status = builder.status;
        consultorio = builder.consultorio;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getAlias() {
        return alias;
    }

    public String getUser() {
        return user;
    }

    public String getSenha() {
        return senha;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public String getStatus() {
        return status;
    }

    public Set<Permissao> getPermissao() {
        return permissao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setPermissao(Set<Permissao> permissao) {
        this.permissao = permissao;
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private String alias;
        private String user;
        private String senha;
        private String status;
        private Consultorio consultorio;
        private Set<Permissao> permissao;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder consultorio(Consultorio consultorio) {
            this.consultorio = consultorio;
            return this;
        }

        public Builder permissao(Set<Permissao> per) {
            this.permissao = per;
            return this;
        }

        public Perfil build() {
            return new Perfil(this);
        }
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "nome='" + nome + '\'' +
                ", alias='" + alias + '\'' +
                ", user='" + user + '\'' +
                ", senha='" + senha + '\'' +
                ", status='" + status + '\'' +
                ", consultorio=" + consultorio +
                '}';
    }
}
