package br.com.devdojo.projetoinicial.bean.agenda.diaria;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.AgendaRules;
import br.com.devdojo.projetoinicial.persistence.model.Agenda;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.service.agenda.AgendaService;
import br.com.devdojo.projetoinicial.service.restricao.RestricaoService;
import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.PATTERN_EEEE_MMMM_HH_MM;
import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.PATTERN_HH_MM;

/**
 * @author André Ribeiro, William Suane on 3/31/17.
 */
@Named
@ViewScoped
public class AgendaDiariaRegisBean implements Serializable {
    private int especialistaId;
    //Usados para manter as datas do filtro durante a navegacão
    private String inicio;
    private String fim;
    private Date date;
    private Date today;
    private List<String> datasRestritas = new ArrayList<>();
    private Set<Agenda> agendasJaCadastradasSet = new HashSet<>();
    private Map<Date, List<Agenda>> dateAgendasMap = new LinkedHashMap<>();
    private final LoginBean loginBean;

    @Inject
    public AgendaDiariaRegisBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws IOException {
        //UTC são 3 horas para frente, se passar das 22:00 é considerado outro dia e bloqueia no calendário
        today = Functions.localDateTimeToDate(LocalDateTime.now().minusHours(3));
        datasRestritas = AgendaRules.gerarDiasRestritos(RestricaoService.getAllRestricoes(), especialistaId, loginBean);
        if (!loginBean.getEspecialistaEspecialidadeMap().containsKey(new Especialista(especialistaId)))
            //Redirecionar para acesso negado quando existir
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paciente/paciente.xhtml");
    }

    public void addDate() {
        if (date != null) {
            dateAgendasMap.put(date, new ArrayList<>(Collections.singletonList(AgendaRules.createAgendaBasicFields(date, AgendaTipoEnum.DIARIO, loginBean, especialistaId))));
            datasRestritas.add(Functions.dateToLocalDate(date).format(DateTimeFormatter.ofPattern("yyyy-M-d")));
            agendasJaCadastradasSet.addAll(AgendaService.getAllAgendas(Collections.singletonList(loginBean.getPerfil().getConsultorio().getId().toString()), especialistaId, AgendaTipoEnum.DIARIO.getId(), date, date));
            date = null;
        }
    }


    public void addTime(Date date) {
        dateAgendasMap.get(date).add(AgendaRules.createAgendaBasicFields(date, AgendaTipoEnum.DIARIO, loginBean, especialistaId));
    }

    public void removeTime(Date date) {
        int inicioFimListSize = dateAgendasMap.get(date).size();
        if (inicioFimListSize > 1) {
            dateAgendasMap.get(date).remove(inicioFimListSize - 1);
            return;
        }
        dateAgendasMap.remove(date);

    }


    @Transactional
    public String save() {
        List<Agenda> agendaList = AgendaRules.addDateToTime(dateAgendasMap);
        agendaList.sort(Comparator.comparing(Agenda::getDataInicio));
        validateConsultaTimeRange(agendaList);
        validateConsultaTimeRangeSavedDates(agendaList);
        AgendaService.create(agendaList);
        return new StringBuilder().append("agenda.xhtml?especialistaId=").append(especialistaId).append("&inicio=").append(inicio).append("&fim=").append(fim).append("&faces-redirect=true").toString();
    }


    /***
     * Faz a validação do início e fim para as datas que estão na tela criardiaria
     * @param agendaList Lista de agendas que se encontra na view
     */
    private void validateConsultaTimeRange(List<Agenda> agendaList) {
        AgendaRules.validateDate(agendaList, "Os horários não podem se sobrepor", PATTERN_HH_MM, loginBean.getLocale());
    }

    /***
     * Faz a validação do início e fim para as datas que estão na tela com as que estão no banco
     * @param agendaList Lista de agendas que se encontra na view
     */
    private void validateConsultaTimeRangeSavedDates(List<Agenda> agendaList) {
        agendaList = new ArrayList<>(agendaList);
        agendaList.addAll(agendasJaCadastradasSet);
        AgendaRules.validateDate(agendaList, "Já existe uma agenda cadastrada para a seguinte data e horário", PATTERN_EEEE_MMMM_HH_MM, loginBean.getLocale());
    }

    public void calcularQuantidadeDeConsultasPossiveisParaOIntervaloDeHoras(Agenda agenda){
        int quantidadeConsultas = AgendaRules.calculateQuantidadeConsultas(agenda);
        agenda.setQuantidadeDeConsultas(quantidadeConsultas);
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

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Map<Date, List<Agenda>> getDateAgendasMap() {
        return dateAgendasMap;
    }

    public void setDateAgendasMap(Map<Date, List<Agenda>> dateAgendasMap) {
        this.dateAgendasMap = dateAgendasMap;
    }

    public List<String> getDatasRestritas() {
        return datasRestritas;
    }

    public void setDatasRestritas(List<String> datasRestritas) {
        this.datasRestritas = datasRestritas;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
