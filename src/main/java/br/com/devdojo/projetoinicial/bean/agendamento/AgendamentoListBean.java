package br.com.devdojo.projetoinicial.bean.agendamento;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.app.ApplicationBean;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.AgendamentoRules;
import br.com.devdojo.projetoinicial.persistence.model.*;
import br.com.devdojo.projetoinicial.persistence.model.dto.EspecialistaDTO;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.service.agenda.AgendaService;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.service.consultorio.ConsultorioService;
import br.com.devdojo.projetoinicial.service.dataloader.EspecialistasConsultorioDataLoader;
import br.com.devdojo.projetoinicial.service.especialistaconvenio.EspecialistaConvenioService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.*;

/**
 * @author André Ribeiro, William Suane on 4/20/17.
 */
@Named
@ViewScoped
public class AgendamentoListBean implements Serializable {

    private Consultorio consultorio;
    private Especialista especialista;
    private Especialidade especialidade;
    private Date dataInicio = new Date();
    private Date dataFim;
    private Consulta consultaPacienteEditing;
    private Agenda agenda;
    private String headerModal;
    private List<Agenda> agendaList;
    private Set<Especialidade> especialidadeSet;
    private Set<EspecialistaDTO> especialistaDTOSet;
    private List<SearchDTO> agendaHorariosList;
    private List<SearchDTO> agendaHorariosModalList;
    private List<Convenio> convenioEspecialistaList;
    private List<String> diasLiberados;
    private SearchDTO novaAgendaHorario;
    private SearchDTO antigaAgendaHorario;
    private int consultorioId;
    private int especialidadeId;
    private int especialistaId;
    private int agendaId;
    private Date dataBuscaInicio;
    private Date dataBuscaFim;
    private Date now = Functions.setTimeDateToFirstMinuteOfDay(new Date());
    private final LoginBean loginBean;
    private final ApplicationBean appBean;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private String agendaListString = "";

    @Inject
    public AgendamentoListBean(LoginBean loginBean, ApplicationBean appBean) {
        this.loginBean = loginBean;
        this.appBean = appBean;
    }

    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        if (consultorioId != 0 && especialistaId != 0) {
            initWithParams();
            return;
        }

        consultorio = loginBean.getPerfil().getConsultorio();
        dataFim = localDateToDate(LocalDate.now().plusDays(3));

        loadComboEspecialidades();
        loadComboEspecialistas();
        filterEspecialistaByEspecialidadeAndSearchAgendasDisponiveis();
    }

    private void initWithParams() throws InterruptedException, ExecutionException, TimeoutException {
        consultorio = ConsultorioService.getById(this.consultorioId);

        Set<EspecialistaDTO> especialistasSet = EspecialistasConsultorioDataLoader.getEspecialistaConsultorioEspecialidadesEnabledSet(consultorio);

        this.especialista = especialistasSet
                .stream()
                .map(EspecialistaDTO::getEspecialista)
                .filter(especialista -> especialista.getId().equals(especialistaId))
                .findFirst()
                .orElse(especialistasSet.stream().findFirst().get().getEspecialista());

        loadComboEspecialidades();
        loadComboEspecialistas();

        this.especialidade = especialidadeSet
                .stream()
                .filter(especialidade -> especialidade.getId().equals(especialidadeId))
                .findAny().orElse(null);

        dataInicio = dataBuscaInicio;
        dataFim = dataBuscaFim;

        searchAgendasDisponiveis();

        search();
    }

    public void search() throws InterruptedException, ExecutionException, TimeoutException {
        agendaHorariosList = ConsultaService.searchHorarios(consultorio.getId(),
                especialista.getId(),
                setTimeDateToFirstMinuteOfDay(dataInicio),
                setTimeDateToMaxMinuteOfDay(dataFim));
        agendaList = CompletableFuture.supplyAsync(() -> AgendaService.getAllAgendas(Collections.singletonList(consultorio.getId().toString()), especialista.getId(), AgendaTipoEnum.DIARIO.getId(), Functions.setTimeDateToFirstMinuteOfDay(new Date()), localDateToDate(LocalDate.now().plusMonths(6))), executor).get(10, TimeUnit.SECONDS);
        convenioEspecialistaList = CompletableFuture.supplyAsync(() -> EspecialistaConvenioService.search(especialista.getId(), consultorio.getId()), executor).get(10, TimeUnit.SECONDS);
    }

    private void searchAgendasDisponiveis() {
        int maximumYear = LocalDate.now().getYear() + 2;
        List<Agenda> agendaList = AgendaService.getAllAgendas(Collections.singletonList(String.valueOf(consultorio.getId())),
                especialista.getId(),
                AgendaTipoEnum.DIARIO.getId(),
                Functions.localDateToDate(LocalDate.now().with(TemporalAdjusters.firstDayOfYear())),
                Functions.localDateToDate(LocalDate.now().withYear(maximumYear).with(TemporalAdjusters.lastDayOfYear())));
        diasLiberados = agendaList.stream().filter(Agenda::getLiberado).map(Agenda::getDataInicio).map(date -> Functions.dateToLocalDate(date).format(DateTimeFormatter.ofPattern("''yyyy-M-d''"))).collect(Collectors.toList());
    }

    private void loadComboEspecialidades() {
        especialidadeSet = EspecialistasConsultorioDataLoader.getEspecialidadesEnabledSet(consultorio)
                .stream()
                .sorted(Comparator.comparing(Especialidade::getEspecialidade))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void loadComboEspecialistas(){
        especialistaDTOSet = EspecialistasConsultorioDataLoader.getEspecialistaConsultorioEspecialidadesEnabledSet(consultorio)
                .stream()
                .filter(especialistaDTO -> especialidade == null || especialistaDTO.getEspecialidades()
                                .stream()
                                .anyMatch(especialidadee -> especialidadee.equals(especialidade)))
                .sorted(Comparator.comparing(especialistaDTO -> especialistaDTO.getEspecialista().getNome()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void clearAgendaHorarioList() {
        clearCalendarios();
        searchAgendasDisponiveis();
    }

    public void clearFieldsAndLoadCombosEspecialidadesAndEspecialistas() {
        clearAllFields();
        loadComboEspecialidades();
        loadComboEspecialistas();
        filterFirstEspecialistaFromEspecialistaSet();
        searchAgendasDisponiveis();
    }

    private void filterFirstEspecialistaFromEspecialistaSet() {
        this.especialista = especialistaDTOSet
                .stream()
                .map(especialistaDTO -> especialistaDTO.getEspecialista())
                .findFirst()
                .orElse(null);

        this.especialistaId = especialista.getId();
    }

    public void filterEspecialistaByEspecialidadeAndSearchAgendasDisponiveis() {
        clearCalendarios();
        loadComboEspecialistas();
        filterFirstEspecialistaFromEspecialistaSet();
        searchAgendasDisponiveis();
    }

    private void clearCalendarios() {
        Functions.clearCollection(agendaHorariosList);
    }

    private void clearAllFields() {
        Functions.clearCollection(especialidadeSet);
        Functions.clearCollection(especialistaDTOSet);
        clearCalendarios();
        this.dataInicio = null;
        this.dataFim = null;
        especialidade = null;
        especialista = null;
    }

    public void onOpenDialogPaciente(Consulta consulta) {
        this.consultaPacienteEditing = consulta;
    }

    public void onOpenDialogAgenda(SearchDTO antigaAgendaHorario, Consulta consulta, Agenda agenda) {
        this.agenda = agenda;
        consultaPacienteEditing = consulta;

        //Utilizado no socket
        SearchDTO antigaAgendaHorarioClone = SerializationUtils.clone(antigaAgendaHorario);
        appBean.getAgendaHorariosInUse().add(antigaAgendaHorarioClone);
        loginBean.getAgendaHorariosInUseSession().add(antigaAgendaHorarioClone);
        this.antigaAgendaHorario = antigaAgendaHorarioClone;

        novaAgendaHorario = antigaAgendaHorario;
        onChangeAgendaDialog();
        //notifyPUSHOpen();
    }

    public void onChangeAgendaDialog() {
        agendaHorariosModalList = AgendamentoRules.searchAgendaHorario(consultorio.getId(), especialista.getId(), agenda);
    }

    public void onCloseDialogPaciente() {
        consultaPacienteEditing = null;
    }

    public void onCloseDialogAgenda() {
        appBean.getAgendaHorariosInUse().remove(antigaAgendaHorario);
        loginBean.getAgendaHorariosInUseSession().remove(antigaAgendaHorario);
        //notifyPUSHClose();
        if (agendaHorariosModalList != null)
            agendaHorariosModalList.clear();
        agendaHorariosModalList = null;
        consultaPacienteEditing = null;
    }

    public void onSelectDataInicio() {
        dataFim = dataInicio;
        clearAgendaHorarioList();
        try {
            search();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < agendaList.size(); i++) {
            agendaListString = agendaListString + "," + agendaList.get(i).getId();
        }

     //   RequestContext ajax = RequestContext.getCurrentInstance();
     //   ajax.update("socketForm");
     //   RequestContext.getCurrentInstance().execute("openSocket()");
    }

    @Transactional
    public void desmarcar() {
        consultaPacienteEditing.setStatus(new Status(5));
        consultaPacienteEditing.setOrigem(Origem.newBuilder().id(1).build());
        ConsultaService.update(consultaPacienteEditing);
        updateTableSocket();
    }

    @Transactional
    public void update() {
        consultaPacienteEditing.setOrigem(Origem.newBuilder().id(1).build());
        consultaPacienteEditing.setHoraInicio(novaAgendaHorario.getDiaInicio());
        consultaPacienteEditing.setHoraFinal(novaAgendaHorario.getDiaFim());
        consultaPacienteEditing.setAgenda(novaAgendaHorario.getAgenda());
        ConsultaService.reagendar(consultaPacienteEditing);
    }

    public void updateTableSocket() {
        agendaHorariosList = ConsultaService.searchHorarios(consultorio.getId(), especialista.getId(), setTimeDateToFirstMinuteOfDay(dataInicio), setTimeDateToMaxMinuteOfDay(dataFim));
    }

    private void notifyPUSHOpen() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/notifyOpen", "");
    }

    /*private void notifyPUSHClose() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/notifyClose", "");
    }*/

    public Date getNow() {return now;}

    public void setNow(Date now) {this.now = now;}

    public int getConsultorioId() {return consultorioId;}

    public void setConsultorioId(int consultorioId) {this.consultorioId = consultorioId;}

    public int getEspecialidadeId() {return especialidadeId;}

    public void setEspecialidadeId(int especialidadeId) {this.especialidadeId = especialidadeId;}

    public int getEspecialistaId() {return especialistaId;}

    public void setEspecialistaId(int especialistaId) {this.especialistaId = especialistaId;}

    public int getAgendaId() {return agendaId;}

    public void setAgendaId(int agendaId) {this.agendaId = agendaId;}

    public Date getDataBuscaInicio() {return dataBuscaInicio;}

    public void setDataBuscaInicio(Date dataBuscaInicio) {this.dataBuscaInicio = dataBuscaInicio;}

    public Date getDataBuscaFim() {return dataBuscaFim;}

    public void setDataBuscaFim(Date dataBuscaFim) {this.dataBuscaFim = dataBuscaFim;}

    public List<SearchDTO> getAgendaHorariosModalList() {return agendaHorariosModalList;}

    public void setAgendaHorariosModalList(List<SearchDTO> agendaHorariosModalList) {this.agendaHorariosModalList = agendaHorariosModalList;}

    public List<Convenio> getConvenioEspecialistaList() {return convenioEspecialistaList;}

    public void setConvenioEspecialistaList(List<Convenio> convenioEspecialistaList) {this.convenioEspecialistaList = convenioEspecialistaList;}

    public SearchDTO getAntigaAgendaHorario() {return antigaAgendaHorario;}

    public void setAntigaAgendaHorario(SearchDTO antigaAgendaHorario) {this.antigaAgendaHorario = antigaAgendaHorario;}

    public Consulta getConsultaPacienteEditing() {return consultaPacienteEditing;}

    public void setConsultaPacienteEditing(Consulta consultaPacienteEditing) {this.consultaPacienteEditing = consultaPacienteEditing;}

    public SearchDTO getNovaAgendaHorario() {return novaAgendaHorario;}

    public void setNovaAgendaHorario(SearchDTO novaAgendaHorario) {this.novaAgendaHorario = novaAgendaHorario;}

    public String getHeaderModal() {return headerModal;}

    public void setHeaderModal(String headerModal) {this.headerModal = headerModal;}

    public List<Agenda> getAgendaList() {return agendaList;}

    public void setAgendaList(List<Agenda> agendaList) {this.agendaList = agendaList;}

    public Agenda getAgenda() {return agenda;}

    public void setAgenda(Agenda agenda) {this.agenda = agenda;}

    public List<SearchDTO> getAgendaHorariosList() {return agendaHorariosList;}

    public void setAgendaHorariosList(List<SearchDTO> agendaHorariosList) {this.agendaHorariosList = agendaHorariosList;}

    public Date getDataInicio() {return dataInicio;}

    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}

    public Date getDataFim() {return dataFim;}

    public List<String> getDiasLiberados() {return diasLiberados;}

    public void setDiasLiberados(List<String> diasLiberados) {this.diasLiberados = diasLiberados;}

    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}

    public Set<Especialidade> getEspecialidadeSet() {return especialidadeSet;}

    public void setEspecialidadeSet(Set<Especialidade> especialidadeSet) {this.especialidadeSet = especialidadeSet;}

    public Especialidade getEspecialidade() {return especialidade;}

    public void setEspecialidade(Especialidade especialidade) {this.especialidade = especialidade;}

    public Especialista getEspecialista() {return especialista;}

    public void setEspecialista(Especialista especialista) {this.especialista = especialista;}

    public Consultorio getConsultorio() {return consultorio;}

    public void setConsultorio(Consultorio consultorio) {this.consultorio = consultorio;}

    public String getAgendaListString() {return agendaListString;}

    public void setAgendaListString(String agendaListString) {this.agendaListString = agendaListString;}

    public Set<EspecialistaDTO> getEspecialistaDTOSet() {return especialistaDTOSet;}

    public void setEspecialistaDTOSet(Set<EspecialistaDTO> especialistaDTOSet) {this.especialistaDTOSet = especialistaDTOSet;}
}