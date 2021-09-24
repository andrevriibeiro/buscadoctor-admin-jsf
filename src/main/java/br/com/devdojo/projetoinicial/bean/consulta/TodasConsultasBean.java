package br.com.devdojo.projetoinicial.bean.consulta;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaEspecialidade;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.localDateToDate;
import static java.util.Arrays.asList;

/**
 * @author André Ribeiro, William Suane on 11/8/17.
 */
@Named
@ViewScoped
public class TodasConsultasBean implements Serializable {
    private final LoginBean loginBean;
    private List<Consulta> consultaList;
    private Set<Especialista> especialistaSet;
    private Date inicio = localDateToDate(LocalDate.now());
    private Date fim = localDateToDate(LocalDate.now().plusDays(1));
    private Especialista especialista;
    private Consulta consulta;
    private List<StatusConsultaEnum> statusConsultaEnumList = asList(StatusConsultaEnum.APROVADA, StatusConsultaEnum.CANCELADA, StatusConsultaEnum.ATENDIDO, StatusConsultaEnum.NAO_COMPARECEU);

    @Inject
    public TodasConsultasBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        loadComboEspecialistas();
        buscar();
    }

    private void loadComboEspecialistas() {
        especialistaSet = loginBean.getConsultorioEspecialistaEspecialidadeMap()
                .get(loginBean.getPerfil().getConsultorio())
                .stream()
                .map(EspecialistaEspecialidade::getEspecialista)
                .sorted(Comparator.comparing(Especialista::getNome))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void buscar() {
        List<Integer> allStatusConsultas = Arrays.stream(StatusConsultaEnum.values())
                .map(StatusConsultaEnum::getId).collect(Collectors.toList());
        int consultorioId = loginBean.getPerfil().getConsultorio().getId();
        int especialistaId = especialista == null ? 0 : especialista.getId();
        consultaList = ConsultaService.searchConsultasByStatusAndEspecialista(consultorioId, especialistaId, inicio, fim, allStatusConsultas);
    }

    @Transactional
    public void updateStatusConsulta(Consulta consulta) {
        consulta.setPerfil(loginBean.getPerfil());
        ConsultaService.update(consulta);
        String status = StatusConsultaEnum.getEnumByStatusId(consulta.getStatus().getId()).getLabel().toLowerCase();
        consultaList.get(consultaList.indexOf(consulta)).getStatus().setNome(status);
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<StatusConsultaEnum> getStatusConsultaEnumList() {
        return statusConsultaEnumList;
    }

    public void setStatusConsultaEnumList(List<StatusConsultaEnum> statusConsultaEnumList) {
        this.statusConsultaEnumList = statusConsultaEnumList;
    }

    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    public Set<Especialista> getEspecialistaSet() {
        return especialistaSet;
    }

    public void setEspecialistaSet(Set<Especialista> especialistaSet) {
        this.especialistaSet = especialistaSet;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }
}