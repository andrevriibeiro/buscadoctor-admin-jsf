package br.com.devdojo.projetoinicial.bean.agenda.mensal;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.AgendaRules;
import br.com.devdojo.projetoinicial.persistence.model.Agenda;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.persistence.model.enums.DiasDaSemanaEnum;
import br.com.devdojo.projetoinicial.service.agenda.AgendaService;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;
import br.com.devdojo.projetoinicial.service.restricao.RestricaoService;
import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.PATTERN_EEEE_MMMM_HH_MM;
import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.PATTERN_HH_MM;

/**
 * @author André Ribeiro on 4/5/17.
 */
@Named
@ViewScoped
public class AgendaMensalRegisBean implements Serializable {
    private int especialistaId;
    //Usados para manter as datas do filtro durante a navegacão
    private String inicio;
    private String fim;
    private int agendaTipoId;
    private AgendaTipoEnum agendaTipoEnum;
    private List<Date> monthsList;
    private List<Date> monthsSelectedList = new ArrayList<>(12);
    private List<DiasDaSemanaEnum> diasDaSemanaList = Arrays.asList(DiasDaSemanaEnum.values());
    private List<DiasDaSemanaEnum> diasDaSemanaSelectedList = new ArrayList<>();
    private Map<DiasDaSemanaEnum, List<Agenda>> diaDaSemanaHorarioMap = new TreeMap<>();
    private Set<LocalDate> diasRestritosSet = new HashSet<>();
    private int[] availableYears = {LocalDate.now().getYear(), LocalDate.now().getYear() + 1, LocalDate.now().getYear() + 2};
    private int yearSelected = LocalDate.now().getYear();
    private final LoginBean loginBean;

    @Inject
    public AgendaMensalRegisBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws IOException {
        cleanAllMonthsAndDays();
        generateDiasRestritos();
        if (!loginBean.getEspecialistaEspecialidadeMap().containsKey(new Especialista(especialistaId)))
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paciente/paciente.xhtml");
        agendaTipoEnum = AgendaTipoEnum.getEnumById(agendaTipoId);
        generateAllMonthsOfTheYear();
    }

    /***
     * Gera objetos do tipo LocalDate dos dias restritos e adiciona ao SET para validar no momento de salvar.
     */
    private void generateDiasRestritos() {
        List<String> datasRestritas = AgendaRules.gerarDiasRestritos(RestricaoService.getAllRestricoes(), especialistaId, loginBean);
        datasRestritas.forEach(dataString -> diasRestritosSet.add(LocalDate.parse(dataString.replace("'", ""), DateTimeFormatter.ofPattern("yyyy-M-d"))));
    }

    public void cleanAllMonthsAndDays(){
        Functions.clearCollection(monthsSelectedList);
        Functions.clearCollection(diasDaSemanaSelectedList);
        diaDaSemanaHorarioMap.clear();
    }

    public void addOrRemoveMeses() {
        if (monthsSelectedList.isEmpty()) {
            diaDaSemanaHorarioMap.clear();
            diasDaSemanaSelectedList.clear();
        }
    }

    public void addOrRemoveDiasDaSemana() {

        if (!monthsSelectedList.isEmpty() && !diasDaSemanaSelectedList.isEmpty()) {
            diasDaSemanaSelectedList.forEach(dia -> {
                if (!diaDaSemanaHorarioMap.containsKey(dia)) {
                    diaDaSemanaHorarioMap.putIfAbsent(dia, new ArrayList<>(3));
                    adicionarCamposHoraInicioHoraFimParaDiaDaSemana(dia);
                }
            });
        }
        diaDaSemanaHorarioMap.keySet().retainAll(diasDaSemanaSelectedList);
    }


    public void adicionarCamposHoraInicioHoraFimParaDiaDaSemana(DiasDaSemanaEnum diaDaSemana) {
        diaDaSemanaHorarioMap.get(diaDaSemana).add(AgendaRules.createAgendaBasicFields(new Date(), AgendaTipoEnum.DIARIO, loginBean, especialistaId, diaDaSemana));
    }

    private Date addTimeToLocalDateTime(LocalDateTime localDateTime, Date time) {
        return Functions.localDateTimeToDate(LocalDateTime.of(localDateTime.toLocalDate(), Functions.dateToLocalTime(time)));
    }

    @Transactional
    public String save() {
        validateTimeFromView();
        List<Agenda> datesAgendaMensal = buildDates();
        List<Agenda> savedAgendas = AgendaService.getAllAgendas(Collections.singletonList(loginBean.getPerfil().getConsultorio().getId().toString()), especialistaId, AgendaTipoEnum.DIARIO.getId(), datesAgendaMensal.get(0).getDataInicio(), datesAgendaMensal.get(datesAgendaMensal.size() - 1).getDataFinal());
        savedAgendas.addAll(datesAgendaMensal);
        AgendaRules.validateDate(savedAgendas, "Já existe uma agenda cadastrada para a seguinte data e horário", PATTERN_EEEE_MMMM_HH_MM, loginBean.getLocale());
        AgendaService.create(datesAgendaMensal);
        return new StringBuilder().append("agenda.xhtml?especialistaId=").append(especialistaId).append("&inicio=").append(inicio).append("&fim=").append(fim).append("&faces-redirect=true").toString();
    }

    /***
     * Cria datas individuais para cada dia da semana dos meses selecionados e adiciona a um objeto agenda
     * @return Lista de agendas, cada uma com um dia do calendário para aquele dia da semana e aquele mês.
     */
    private List<Agenda> buildDates() {
        List<Agenda> datesAgendaMensal = new ArrayList<>(60);
        diaDaSemanaHorarioMap.keySet().forEach(key -> {
            for (Date month : monthsSelectedList) {
                LocalDateTime start = Functions.dateToLocalDateTime(month).withYear(yearSelected).with(TemporalAdjusters.firstDayOfMonth());
                LocalDateTime end = Functions.dateToLocalDateTime(month).withYear(yearSelected).with(TemporalAdjusters.lastDayOfMonth());
                LocalDateTime nextOrSameDayOfWeek = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(key.getLabelEnglish())));
                while (!nextOrSameDayOfWeek.isAfter(end)) {
                    for (Agenda agenda : diaDaSemanaHorarioMap.get(key)) {
                        if (diasRestritosSet.contains(nextOrSameDayOfWeek.toLocalDate()))
                            continue;
                        Date inicio = addTimeToLocalDateTime(nextOrSameDayOfWeek, agenda.getDataInicio());
                        Date fim = addTimeToLocalDateTime(nextOrSameDayOfWeek, agenda.getDataFinal());
                        datesAgendaMensal.add(AgendaRules.createAgendaBasicFields(inicio, fim, AgendaTipoEnum.DIARIO, loginBean, especialistaId, agenda.getTempoConsulta()));
                    }
                    int plusWeek = agendaTipoEnum.equals(AgendaTipoEnum.MENSAL) ? 1 : 2; //Se for mensal pula uma semana, se for quinzenal 2
                    nextOrSameDayOfWeek = nextOrSameDayOfWeek.plusWeeks(plusWeek);
                }
            }
        });
        return datesAgendaMensal;
    }


    /***
     * Faz a validação para verificar se alguma hora está sobreposta no mesmo dia
     */
    private void validateTimeFromView() {
        diaDaSemanaHorarioMap.keySet().forEach(key -> AgendaRules.validateDate(diaDaSemanaHorarioMap.get(key), "Os horários não pode se sobrepor", PATTERN_HH_MM, loginBean.getLocale()));
    }

    public void removerUltimaAgendaCriadaParaDeterminadoDiaDaSemana(DiasDaSemanaEnum diaDaSemana) {
        int quantidadeDeAgendasParaDiaDaSemana = diaDaSemanaHorarioMap.get(diaDaSemana).size();
        if (quantidadeDeAgendasParaDiaDaSemana == 1) {
            diaDaSemanaHorarioMap.remove(diaDaSemana);
            diasDaSemanaSelectedList.remove(diaDaSemana);
            return;
        }

        quantidadeDeAgendasParaDiaDaSemana--;
        diaDaSemanaHorarioMap.get(diaDaSemana).remove(quantidadeDeAgendasParaDiaDaSemana);
    }

    private void generateAllMonthsOfTheYear() {
        LocalDate begin = yearSelected > LocalDate.now().getYear() ? LocalDate.now().with(TemporalAdjusters.firstDayOfYear()) : LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        monthsList = new ArrayList<>(12);
        do {
            monthsList.add(Functions.localDateToDate(begin));
        } while ((begin = begin.plusMonths(1)).isBefore(end));

    }

    public int[] getAvailableYears() {
        return availableYears;
    }

    public void setAvailableYears(int[] availableYears) {
        this.availableYears = availableYears;
    }

    public int getYearSelected() {
        return yearSelected;
    }

    public void setYearSelected(int yearSelected) {
        this.yearSelected = yearSelected;
    }

    public void calcularQuantidadeDeConsultasPossiveisParaOIntervaloDeHoras(Agenda agenda) {
        int quantidadeConsultas = AgendaRules.calculateQuantidadeConsultas(agenda);
        agenda.setQuantidadeDeConsultas(quantidadeConsultas);
    }

    public Map<DiasDaSemanaEnum, List<Agenda>> getDiaDaSemanaHorarioMap() {
        return diaDaSemanaHorarioMap;
    }

    public void setDiaDaSemanaHorarioMap(Map<DiasDaSemanaEnum, List<Agenda>> diaDaSemanaHorarioMap) {
        this.diaDaSemanaHorarioMap = diaDaSemanaHorarioMap;
    }

    public List<DiasDaSemanaEnum> getDiasDaSemanaList() {
        return diasDaSemanaList;
    }

    public void setDiasDaSemanaList(List<DiasDaSemanaEnum> diasDaSemanaList) {
        this.diasDaSemanaList = diasDaSemanaList;
    }

    public List<DiasDaSemanaEnum> getDiasDaSemanaSelectedList() {
        return diasDaSemanaSelectedList;
    }

    public void setDiasDaSemanaSelectedList(List<DiasDaSemanaEnum> diasDaSemanaSelectedList) {
        this.diasDaSemanaSelectedList = diasDaSemanaSelectedList;
    }

    public List<Date> getMonthsSelectedList() {
        return monthsSelectedList;
    }

    public void setMonthsSelectedList(List<Date> monthsSelectedList) {
        this.monthsSelectedList = monthsSelectedList;
    }

    public List<Date> getMonthsList() {
        return monthsList;
    }

    public void setMonthsList(List<Date> monthsList) {
        this.monthsList = monthsList;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
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

    public int getAgendaTipoId() {
        return agendaTipoId;
    }

    public void setAgendaTipoId(int agendaTipoId) {
        this.agendaTipoId = agendaTipoId;
    }

    public AgendaTipoEnum getAgendaTipoEnum() {
        return agendaTipoEnum;
    }

    public void setAgendaTipoEnum(AgendaTipoEnum agendaTipoEnum) {
        this.agendaTipoEnum = agendaTipoEnum;
    }
}
