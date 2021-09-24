package br.com.devdojo.projetoinicial.bean.app;

import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.service.convenio.ConvenioService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
@ApplicationScoped
@Named
public class ApplicationBean implements Serializable {
    private List<Convenio> convenioList;
    private Set<SearchDTO> agendaHorariosInUse = new HashSet<>();

    @PostConstruct
    public void init() {
        convenioList = ConvenioService.getAllConvenios();
    }

    public Set<SearchDTO> getAgendaHorariosInUse() {
        return agendaHorariosInUse;
    }

    public void setAgendaHorariosInUse(Set<SearchDTO> agendaHorariosInUse) {
        this.agendaHorariosInUse = agendaHorariosInUse;
    }

    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }
}
