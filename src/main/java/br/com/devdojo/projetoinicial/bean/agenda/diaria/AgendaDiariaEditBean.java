package br.com.devdojo.projetoinicial.bean.agenda.diaria;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.AgendaRules;
import br.com.devdojo.projetoinicial.persistence.model.Agenda;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.service.agenda.AgendaService;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.service.restricao.RestricaoService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.PATTERN_EEEE_MMMM_HH_MM;
import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.PATTERN_HH_MM;

/**
 * @author Andre Ribeiro on 4/4/2017.
 */
@Named
@ViewScoped
public class AgendaDiariaEditBean implements Serializable {
    private int especialistaId;
    //Inicio e fim Usados para manter as datas do filtro durante a navegacão
    private String inicio;
    private String fim;
    private Date dateAgendaInicio;
    private Date dateAgendaFim;
    private AgendaTipoEnum agendaTipoEnum = AgendaTipoEnum.DIARIO;
    private Map<Date, List<Agenda>> dateAgendasMap = new TreeMap<>();
    private Set<Agenda> agendasWithConsulta = new HashSet<>(1);
    private List<Agenda> agendasRemoved = new ArrayList<>(5);
    private List<String> datasRestritas;
    private Date tempDateToUpdate; // Usado para trocar a data de uma agenda e manter o horário
    private Date today; // data de hoje para evitar do usuário escolher uma data passada no calendário
    private Set<Date> datesChanged = new HashSet<>(); // usado para colocar cor nos label, pois é ordenado automaticamente
    private List<String> daysOfWeekSelected = new ArrayList<>();
    private Date horaInicioRep;
    private Date horaFinalRep;
    private int tempoConsultaRep;
    private final LoginBean loginBean;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Inject
    public AgendaDiariaEditBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws IOException {
        //Redirecionar para acesso negado quando não existir
        if (!loginBean.getEspecialistaEspecialidadeMap().containsKey(new Especialista(especialistaId)))
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paciente/paciente.xhtml");
        datasRestritas = AgendaRules.gerarDiasRestritos(RestricaoService.getAllRestricoes(), especialistaId, loginBean);
        today = Functions.localDateTimeToDate(LocalDateTime.now().minusHours(3));
        search();
    }

    private void atualizarQuantidadeDeConsultasNaListaDeAgenda(List<Agenda> agendaList) {
        agendaList.forEach(agenda -> {
            agenda.setQuantidadeDeConsultas(AgendaRules.calculateQuantidadeConsultas(agenda));
        });
    }

    public void search() {
        List<Agenda> agendaList = AgendaService.getAllAgendas(Collections.singletonList(loginBean.getPerfil().getConsultorio().getId().toString()),
                especialistaId,
                agendaTipoEnum.getId(),
                dateAgendaInicio,
                dateAgendaFim);
        List<Consulta> consultaList = ConsultaService.search(agendaList.stream().map(agenda -> agenda.getId().toString()).collect(Collectors.toList()));
        agendasWithConsulta = consultaList.stream().map(Consulta::getAgenda).collect(Collectors.toSet());
        agendaList = filterDaysOfWeek(agendaList);
        atualizarQuantidadeDeConsultasNaListaDeAgenda(agendaList);
        dateAgendasMap = agendaList.stream().collect(Collectors.groupingBy(Agenda::getDataInicio, TreeMap::new, Collectors.toList()));
    }

    private List<Agenda> filterDaysOfWeek(List<Agenda> agendaList) {
        if (daysOfWeekSelected.size() > 0) {
            return agendaList.stream().filter(agenda -> daysOfWeekSelected.contains(Functions.dateToLocalDate(agenda.getDataInicio()).getDayOfWeek().toString())).collect(Collectors.toList());
        }
        return agendaList;
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        List<Agenda> agendaList = validateBeforeUpdate();
        int statusDelete = !agendasRemoved.isEmpty() ? CompletableFuture.supplyAsync(() -> AgendaService.delete(agendasRemoved), executor).get(10, TimeUnit.SECONDS) : 200;
        int statusUpdate = !agendaList.isEmpty() ? CompletableFuture.supplyAsync(() -> AgendaService.update(agendaList), executor).get(10, TimeUnit.SECONDS) : 200;
        if (statusDelete != 200 || statusUpdate != 200) {
            FacesUtils.addErrorMessageNoBundle("Ocorreu um erro ao atualizar as agendas", false);
            return null;
        }
        return new StringBuilder().append("agenda.xhtml?especialistaId=").append(especialistaId).append("&inicio=").append(inicio).append("&fim=").append(fim).append("&faces-redirect=true").toString();
    }

    private List<Agenda> validateBeforeUpdate() {
        if (dateAgendasMap.size() > 0) {
            validateDateTimeFromView();
            List<Agenda> agendaList = AgendaRules.addDateToTime(dateAgendasMap);
            validateDateTimeFromDatabase(agendaList);
            return agendaList;
        }
        return new ArrayList<>(1);
    }

    public void remove(Agenda agenda, Date key) {
        agendasRemoved.add(agenda);
        if (dateAgendasMap.get(key).size() == 1) dateAgendasMap.remove(key);
        else dateAgendasMap.get(key).remove(agenda);

    }

    public void changeDate(Date key) {
        Date newDateKey = Functions.localDateTimeToDate(AgendaRules.addTimeToDate(tempDateToUpdate, key));
        if (!dateAgendasMap.containsKey(newDateKey)) {
            dateAgendasMap.get(key).forEach(agenda -> {
                agenda.setDataInicio(Functions.localDateTimeToDate(AgendaRules.addTimeToDate(tempDateToUpdate, agenda.getDataInicio())));
                agenda.setDataFinal(Functions.localDateTimeToDate(AgendaRules.addTimeToDate(tempDateToUpdate, agenda.getDataFinal())));
            });
            datesChanged.add(newDateKey);
            dateAgendasMap.put(newDateKey, dateAgendasMap.get(key));
            dateAgendasMap.remove(key);
            tempDateToUpdate = null;

        }
    }

    private void validateDateTimeFromView() {
        AgendaRules.validateDate(AgendaRules.addDateToTime(dateAgendasMap), "Os horários não podem se sobrepor", PATTERN_HH_MM, loginBean.getLocale());
    }

    private void validateDateTimeFromDatabase(List<Agenda> agendaList) {
        List<Agenda> agendasSaved = search(agendaList.get(0).getDataInicio(), agendaList.get(agendaList.size() - 1).getDataFinal(), agendaList);
        agendasSaved.addAll(agendaList);
        AgendaRules.validateDate(agendasSaved, "Já existe uma agenda cadastrada para a seguinte data e horário", PATTERN_EEEE_MMMM_HH_MM, loginBean.getLocale());
        agendasSaved.clear();
    }

    public void replicarHorariosParaAgendasSemConsulta() {

        dateAgendasMap.keySet()
                .forEach(key -> dateAgendasMap.get(key)
                        .forEach(agenda -> {
                            if (!agendasWithConsulta.contains(agenda)) {
                                Date inicio = horaInicioRep != null ? horaInicioRep : agenda.getDataInicio();
                                Date fim = horaFinalRep != null ? horaFinalRep : agenda.getDataFinal();
                                int tempoConsulta = tempoConsultaRep != 0 ? tempoConsultaRep : agenda.getTempoConsulta();
                                agenda.setDataInicio(inicio);
                                agenda.setDataFinal(fim);
                                agenda.setTempoConsulta(tempoConsulta);
                            }
                        }));
    }

    private List<Agenda> search(Date inicio, Date fim, List<Agenda> agendasExistentes) {
        return AgendaService.getAllAgendas(Collections.singletonList(loginBean.getPerfil().getConsultorio().getId().toString()),
                especialistaId,
                agendaTipoEnum.getId(),
                inicio,
                fim,
                agendasExistentes);
    }

    public void calcularQuantidadeDeConsultasPossiveisParaOIntervaloDeHoras(Agenda agenda) {
        int quantidadeConsultas = AgendaRules.calculateQuantidadeConsultas(agenda);
        agenda.setQuantidadeDeConsultas(quantidadeConsultas);
    }


    public Date getHoraInicioRep() {
        return horaInicioRep;
    }

    public void setHoraInicioRep(Date horaInicioRep) {
        this.horaInicioRep = horaInicioRep;
    }

    public Date getHoraFinalRep() {
        return horaFinalRep;
    }

    public void setHoraFinalRep(Date horaFinalRep) {
        this.horaFinalRep = horaFinalRep;
    }

    public int getTempoConsultaRep() {
        return tempoConsultaRep;
    }

    public void setTempoConsultaRep(int tempoConsultaRep) {
        this.tempoConsultaRep = tempoConsultaRep;
    }

    public List<String> getDaysOfWeekSelected() {
        return daysOfWeekSelected;
    }

    public void setDaysOfWeekSelected(List<String> daysOfWeekSelected) {
        this.daysOfWeekSelected = daysOfWeekSelected;
    }

    public List<String> getDatasRestritas() {
        return datasRestritas;
    }

    public void setDatasRestritas(List<String> datasRestritas) {
        this.datasRestritas = datasRestritas;
    }

    public Set<Date> getDatesChanged() {
        return datesChanged;
    }

    public Date getTempDateToUpdate() {
        return tempDateToUpdate;
    }

    public void setTempDateToUpdate(Date tempDateToUpdate) {
        this.tempDateToUpdate = tempDateToUpdate;
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

    public Set<Agenda> getAgendasWithConsulta() {
        return agendasWithConsulta;
    }

    public void setAgendasWithConsulta(Set<Agenda> agendasWithConsulta) {
        this.agendasWithConsulta = agendasWithConsulta;
    }

    public Map<Date, List<Agenda>> getDateAgendasMap() {
        return dateAgendasMap;
    }

    public void setDateAgendasMap(Map<Date, List<Agenda>> dateAgendasMap) {
        this.dateAgendasMap = dateAgendasMap;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public Date getDateAgendaInicio() {
        return dateAgendaInicio;
    }

    public void setDateAgendaInicio(Date dateAgendaInicio) {
        this.dateAgendaInicio = dateAgendaInicio;
    }

    public Date getDateAgendaFim() {
        return dateAgendaFim;
    }

    public void setDateAgendaFim(Date dateAgendaFim) {
        this.dateAgendaFim = dateAgendaFim;
    }

}
