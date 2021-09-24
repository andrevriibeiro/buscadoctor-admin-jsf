package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Set;

/**
 * Classe da que representa a Agenda
 *
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class Agenda extends AbstractEntity {

    private Consultorio consultorio;
    private Especialista especialista;
    @JsonProperty("datainicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataInicio;
    @JsonProperty("datafinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataFinal;
    @JsonProperty("tempoconsulta")
    private Integer tempoConsulta;
    @JsonProperty("diasemana")
    private Integer diaSemana;
    private Boolean liberado;
    @JsonProperty("tipoagenda")
    private TipoAgenda tipoAgenda;

    private Set<Restricao> restricao;

    private String observacao;
    private Date createdAt;
    @JsonProperty("visivelapp")
    private Boolean visivelApp;
    @JsonIgnore
    private transient int quantidadeDeConsultas;
    public Agenda() {
    }

    public Agenda(int id) {
        this.id = id;
    }


    private Agenda(Builder builder) {
        setId(builder.id);
        consultorio = builder.consultorio;
        especialista = builder.especialista;
        dataInicio = builder.dataInicio;
        dataFinal = builder.dataFinal;
        tempoConsulta = builder.tempoConsulta;
        diaSemana = builder.diaSemana;
        liberado = builder.liberado;
        tipoAgenda = builder.tipoAgenda;
        restricao = builder.restricao;
        observacao = builder.observacao;
        createdAt = builder.createdAt;
        visivelApp = builder.visivelApp;

    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {

        private Integer id;
        private Consultorio consultorio;
        private Especialista especialista;
        private Date dataInicio;
        private Date dataFinal;
        private Integer tempoConsulta;
        private Integer diaSemana;
        private Boolean liberado;
        private TipoAgenda tipoAgenda;
        private Set<Restricao> restricao;
        private String observacao;
        private Date createdAt;
        private Boolean visivelApp;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder consultorio(Consultorio val) {
            consultorio = val;
            return this;
        }

        public Builder especialista(Especialista val) {
            especialista = val;
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

        public Builder tempoConsulta(Integer val) {
            tempoConsulta = val;
            return this;
        }

        public Builder diaSemana(Integer val) {
            diaSemana = val;
            return this;
        }

        public Builder liberado(Boolean val) {
            liberado = val;
            return this;
        }

        public Builder tipoAgenda(TipoAgenda val) {
            tipoAgenda = val;
            return this;
        }

        public Builder restricao(Set<Restricao> val) {
            restricao = val;
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

        public Builder visivelApp(Boolean val) {
            visivelApp = val;
            return this;
        }

        public Agenda build() {
            return new Agenda(this);
        }
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getTempoConsulta() {
        return tempoConsulta;
    }

    public void setTempoConsulta(Integer tempoConsulta) {
        this.tempoConsulta = tempoConsulta;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Boolean getLiberado() {
        return liberado;
    }

    public void setLiberado(Boolean liberado) {
        this.liberado = liberado;
    }

    public TipoAgenda getTipoAgenda() {
        return tipoAgenda;
    }

    public void setTipoAgenda(TipoAgenda tipoAgenda) {
        this.tipoAgenda = tipoAgenda;
    }

    public Set<Restricao> getRestricao() {
        return restricao;
    }

    public void setRestricao(Set<Restricao> restricao) {
        this.restricao = restricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getVisivelApp() {
        return visivelApp;
    }

    public void setVisivelApp(Boolean visivelApp) {
        this.visivelApp = visivelApp;
    }

    public int getQuantidadeDeConsultas() {
        return quantidadeDeConsultas;
    }

    public void setQuantidadeDeConsultas(int quantidadeDeConsultas) {
        this.quantidadeDeConsultas = quantidadeDeConsultas;
    }
}
