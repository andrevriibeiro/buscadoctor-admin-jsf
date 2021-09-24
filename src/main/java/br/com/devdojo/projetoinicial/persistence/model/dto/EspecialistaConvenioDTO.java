package br.com.devdojo.projetoinicial.persistence.model.dto;

import br.com.devdojo.projetoinicial.persistence.model.AbstractEntity;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;

import java.util.List;

/**
 * Created by Andre Ribeiro on 02/06/2017.
 */
public class EspecialistaConvenioDTO extends AbstractEntity {

    private Especialista especialista;
    private List<Convenio> convenios;
    private Integer consultorio;

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public List<Convenio> getConvenios() {
        return convenios;
    }

    public void setConvenios(List<Convenio> convenios) {
        this.convenios = convenios;
    }

    public Integer getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Integer consultorio) {
        this.consultorio = consultorio;
    }
}
