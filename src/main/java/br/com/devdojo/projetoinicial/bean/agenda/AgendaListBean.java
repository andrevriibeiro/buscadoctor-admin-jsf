package br.com.devdojo.projetoinicial.bean.agenda;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Agenda;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.agenda.AgendaService;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.FacesUtils.addErrorMessage;
import static br.com.devdojo.projetoinicial.utils.FacesUtils.addSuccessMessage;

/**
 * @author André Ribeiro on 3/30/17.
 */
@Named
@ViewScoped
public class AgendaListBean implements Serializable {
    private int especialistaId;
    private String especialistaNome;
    private AgendaTipoEnum agendaTipoEnum = AgendaTipoEnum.DIARIO;
    private List<Agenda> agendaList;
    private Agenda agenda = new Agenda();
    private String inicio;
    private String fim;
    private Date today;
    private Date firstDayOfMonth;
    private Set<Agenda> agendasComConsulta;
    private final LoginBean loginBean;
    private static final Logger LOGGER = LoggerFactory.getLogger(AgendaListBean.class);
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Inject
    public AgendaListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        restrictDates();
        LocalDate localDateInicio = inicio == null ? LocalDate.now().withDayOfMonth(1) : Functions.dateStringDDMMYYYToLocalDate(inicio);
        LocalDate localDateFim = fim == null ? LocalDate.now().withDayOfMonth(localDateInicio.lengthOfMonth()) : Functions.dateStringDDMMYYYToLocalDate(fim);
        agenda.setDataInicio(Functions.localDateToDate(localDateInicio));
        agenda.setDataFinal(Functions.localDateToDate(localDateFim));
        search();
    }

    public void search() throws InterruptedException, ExecutionException, TimeoutException {
        int consultorioId = loginBean.getPerfil().getConsultorio().getId();
        List<Consulta> consultas = CompletableFuture.supplyAsync(() -> ConsultaService.search(StatusConsultaEnum.APROVADA.getId(), consultorioId, especialistaId, agenda.getDataInicio(), agenda.getDataFinal()), executor).get(10, TimeUnit.SECONDS);
        agendaList = CompletableFuture.supplyAsync(() -> AgendaService.getAllAgendas(Collections.singletonList(String.valueOf(consultorioId)),
                especialistaId,
                agendaTipoEnum.getId(),
                agenda.getDataInicio(),
                agenda.getDataFinal()), executor).get(10, TimeUnit.SECONDS);
        agendasComConsulta = consultas.stream().map(Consulta::getAgenda).collect(Collectors.toSet());
    }

    @Transactional
    public void onCellEdit(Agenda agenda) {
        try {
            AgendaService.update(Collections.singletonList(agenda));
            addSuccessMessage("successUpdateAgenda", false);
        } catch (Exception e) {
            LOGGER.error("Erro ao editar o Especialista", e);
            addErrorMessage("errorUpdate", true);
        }
    }

    private void restrictDates() {
        today = Functions.localDateTimeToDate(LocalDateTime.now().minusHours(3).with(LocalTime.MIN));
        firstDayOfMonth = Functions.localDateToDate(LocalDate.now().withDayOfMonth(1));
    }

    public Set<Agenda> getAgendasComConsulta() {
        return agendasComConsulta;
    }

    public void setAgendasComConsulta(Set<Agenda> agendasComConsulta) {
        this.agendasComConsulta = agendasComConsulta;
    }

    public Date getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setFirstDayOfMonth(Date firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    public AgendaTipoEnum getAgendaTipoEnum() {
        return agendaTipoEnum;
    }

    public void setAgendaTipoEnum(AgendaTipoEnum agendaTipoEnum) {
        this.agendaTipoEnum = agendaTipoEnum;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public String getEspecialistaNome() {return especialistaNome;}

    public void setEspecialistaNome(String especialistaNome) {this.especialistaNome = especialistaNome;}
}
