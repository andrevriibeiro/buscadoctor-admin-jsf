package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.1
 * @since 1.0.0
 */
public class Consulta extends AbstractEntity {

    private Paciente paciente;
    private Agenda agenda;
    private Status status;
    private Convenio convenio;
    private Especialidade especialidade;
    @JsonProperty("horaInicio")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date horaInicio;
    private Date horaFinal;
    private String comentario;
    private Boolean notificado;
    private Perfil perfil;
    private Date createdAt;
    private Origem origem;

    public Consulta() {
    }

    private Consulta(Builder builder) {
        this.id = builder.id;
        this.paciente = builder.paciente;
        this.agenda = builder.agenda;
        this.status = builder.status;
        this.convenio = builder.convenio;
        this.horaInicio = builder.horaInicio;
        this.horaFinal = builder.horaFinal;
        this.comentario = builder.comentario;
        this.notificado = builder.notificado;
        this.perfil = builder.perfil;
        this.especialidade = builder.especialidade;
        this.createdAt = builder.createdAt;
        this.origem = builder.origem;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newConsulta() {
        return new Builder();
    }

    public static final class Builder {

        private Integer id;
        private Paciente paciente;
        private Agenda agenda;
        private Status status;
        private Convenio convenio;
        private Date horaInicio;
        private Date horaFinal;
        private String comentario;
        private Boolean notificado;
        private Perfil perfil;
        private Especialidade especialidade;
        private Date createdAt;
        private Origem origem;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder paciente(Paciente paciente) {
            this.paciente = paciente;
            return this;
        }

        public Builder agenda(Agenda agenda) {
            this.agenda = agenda;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder convenio(Convenio convenio) {
            this.convenio = convenio;
            return this;
        }

        public Builder horaInicio(Date date) {
            horaInicio = date;
            return this;
        }

        public Builder horaFinal(Date date) {
            horaFinal = date;
            return this;
        }

        public Builder comentario(String comentario) {
            this.comentario = comentario;
            return this;
        }

        public Builder notificado(boolean notificado) {
            this.notificado = notificado;
            return this;
        }

        public Builder perfil(Perfil perfil) {
            this.perfil = perfil;
            return this;
        }

        public Builder createdAt(Date date) {
            createdAt = date;
            return this;
        }

        public Builder especialidade(Especialidade especialidade) {
            this.especialidade = especialidade;
            return this;
        }

        public Consulta build() {
            return new Consulta(this);
        }

        public Builder origem(Origem origem) {
            this.origem = origem;
            return this;
        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
