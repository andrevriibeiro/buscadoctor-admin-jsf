package br.com.devdojo.projetoinicial.persistence.model.dto;

import br.com.devdojo.projetoinicial.persistence.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author André Ribeiro, William Suane on 4/22/17.
 */
public class SearchDTO implements Serializable {
    @JsonProperty("id")
    private int agendaId;
    @JsonProperty("diainicio")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date diaInicio;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("diafim")
    private Date diaFim;
    private Date dia;
    private boolean disponivel;
    private Consultorio consultorio;
    private Especialista especialista;
    @JsonProperty("tipo")
    private TipoAgenda tipoAgenda;
    private List<Consulta> consultas;
    private Agenda agenda;
    private Consulta consulta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchDTO searchDTO = (SearchDTO) o;

        if (!diaInicio.equals(searchDTO.diaInicio)) return false;
        if (!consultorio.equals(searchDTO.consultorio)) return false;
        return consulta != null ? consulta.equals(searchDTO.consulta) : searchDTO.consulta == null;
    }

    @Override
    public int hashCode() {
        int result = diaInicio.hashCode();
        result = 31 * result + consultorio.hashCode();
        result = 31 * result + (consulta != null ? consulta.hashCode() : 0);
        return result;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public int getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(int agendaId) {
        this.agendaId = agendaId;
    }

    public Date getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(Date diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Date getDiaFim() {
        return diaFim;
    }

    public void setDiaFim(Date diaFim) {
        this.diaFim = diaFim;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
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

    public TipoAgenda getTipoAgenda() {
        return tipoAgenda;
    }

    public void setTipoAgenda(TipoAgenda tipoAgenda) {
        this.tipoAgenda = tipoAgenda;
    }

}
