package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Classe que representa o Paciente
 *
 * @author Gabriel Francisco <gabfssilva@gmail.com>, Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.1
 * @since 1.0.0
 */
public class Paciente extends AbstractEntity {
    @JsonProperty("numeroregistro")
    private Integer numeroRegistro;
    private Usuario usuario;
    private Consultorio consultorio;
    private String observacao;
    private Date createdAt;


    private Consulta ultimaConsulta;

    public Paciente() {
    }

    private Paciente(Builder builder) {
        id = builder.id;
        numeroRegistro = builder.numeroRegistro;
        usuario = builder.usuario;
        consultorio = builder.consultorio;
        observacao = builder.observacao;
        createdAt = builder.createdAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getNumeroRegistro() {
        return numeroRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public String getObservacao() {
        return observacao;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setObservacao(String observacao) {this.observacao = observacao;}

    public Consulta getUltimaConsulta() {
        return ultimaConsulta;
    }

    public void setUltimaConsulta(Consulta ultimaConsulta) {
        this.ultimaConsulta = ultimaConsulta;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", numeroRegistro=" + numeroRegistro +
                ", usuario=" + usuario +
                ", observacao='" + observacao + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public static final class Builder {

        private Integer id;
        private Integer numeroRegistro;
        private Usuario usuario;
        private Consultorio consultorio;
        private String observacao;
        private Date createdAt;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder numeroregistro(Integer val) {
            numeroRegistro = val;
            return this;
        }

        public Builder usuario(Usuario val) {
            usuario = val;
            return this;
        }

        public Builder consultorio(Consultorio val) {
            consultorio = val;
            return this;
        }

        public Builder observacao(String val) {
            observacao = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Paciente build() {
            return new Paciente(this);
        }
    }
}
