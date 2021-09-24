package br.com.devdojo.projetoinicial.bean.consulta;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.AgendamentoRules;
import br.com.devdojo.projetoinicial.persistence.model.*;
import br.com.devdojo.projetoinicial.persistence.model.dto.ConsultaDTO;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.agenda.AgendaService;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.service.procedimentoespecialista.ProcedimentoEspecialistaService;
import br.com.devdojo.projetoinicial.service.usuario.UsuarioService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.clearCollection;
import static br.com.devdojo.projetoinicial.utils.Functions.localDateToDate;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author André Ribeiro, William Suane on 5/1/17.
 *         Regra básica para entender essa classe:
 *         A busca feita no datatable é por usuário, esse usuário encontrado é colocado dentro de um objeto paciente, nesse momento
 *         não sabemos se existe um paciente ou não, a API ficará responsável por verificar se já existe um na criação da consulta,
 *         só é preciso colocar o usuário dentro de um objeto paciente.
 */
@Named
@ViewScoped
public class ConsultaRegisterBean implements Serializable {
    private int consultorioId;
    private int especialidadeId;
    private int especialistaId;
    private int agendaId;
    private Date dataBuscaInicio;
    private Date dataBuscaFim;
    private Date diaHoraInicio;
    private Date diaHoraFim;
    private Consultorio consultorio;
    private Especialista especialista;
    private Especialidade especialidade;
    private String backURL;
    private Paciente paciente;
    private Procedimento procedimento;
    private TipoProcedimento tipoProcedimento;
    private SearchDTO agendaHorarioSessao;
    private Usuario usuarioSelected;
    private List<Usuario> usuarioList;
    private List<Especialidade> especialidadeList;
    private List<ProcedimentoConvenio> procedimentoConvenioList;
    private List<ProcedimentoEspecialista> procedimentoEspecialistaList;
    private List<Procedimento> procedimentoList;
    private List<Agenda> agendaList;
    private List<SearchDTO> agendaHorariosModalList;
    private List<SearchDTO> agendaHorariosSessaoList = new ArrayList<>();
    private List<TipoProcedimento> tipoProcedimentoList;
    private Set<Convenio> convenioList;
    private ProcedimentoEspecialista procedimentoEspecialista;
    private Agenda agendaSessao;
    private boolean unicaGuiaSessao = true;
    private boolean consultaRetorno = false;
    private boolean novoUsuario = false;
    private boolean btnNovoUsuario;
    private Consulta consulta = new Consulta();
    private final LoginBean loginBean;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private String dataSocketFormat;

    @Inject
    public ConsultaRegisterBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        procedimentoEspecialistaList = ProcedimentoEspecialistaService.search(especialistaId, 0, consultorioId, true);
        agendaList = CompletableFuture.supplyAsync(() -> AgendaService.getAllAgendas(Collections.singletonList(String.valueOf(consultorioId)), especialistaId, AgendaTipoEnum.DIARIO.getId(), new Date(), localDateToDate(LocalDate.now().plusMonths(6))), executor).get(10, TimeUnit.SECONDS);
        convenioList = procedimentoEspecialistaList.stream().map(ProcedimentoEspecialista::getProcedimentoConvenio).map(ProcedimentoConvenio::getConvenio).sorted(Comparator.comparing(Convenio::getNome)).collect(Collectors.toCollection(LinkedHashSet::new));
        procedimentoConvenioList = procedimentoEspecialistaList.stream().map(ProcedimentoEspecialista::getProcedimentoConvenio).collect(Collectors.toList());
        agendaList.remove(new Agenda(agendaId));
        backURL = prepareBackURL();
        prepareConsultaObject();
        extractInformationFromLogin();
        paciente = newPaciente();
        btnNovoUsuario = true;
        dataSocketFormat = Functions.formateDateToStringYYYYMMDDHHMMSS(diaHoraInicio);
        dataSocketFormat = dataSocketFormat.replace(" ", "T");
    }

    public void searchUsuarios() {
        btnNovoUsuario = false;
        usuarioList = UsuarioService.searchUsuarios(paciente.getUsuario());

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
    }

    public void rowSelected(SelectEvent event) {
        usuarioSelected = (Usuario) event.getObject();
        usuarioSelected.setCpf(usuarioSelected.getCpf() == null ? this.paciente.getUsuario().getCpf() : this.usuarioSelected.getCpf());
        usuarioSelected.setBirthday(usuarioSelected.getBirthday() == null ? this.paciente.getUsuario().getBirthday() : usuarioSelected.getBirthday());
        usuarioSelected.setNome(usuarioSelected.getNome() == null ? this.paciente.getUsuario().getNome() : usuarioSelected.getNome());
        usuarioSelected.setCelular(usuarioSelected.getCelular() == null ? this.paciente.getUsuario().getCelular() : usuarioSelected.getCelular());
        usuarioSelected.setTelefoneResidencia(usuarioSelected.getTelefoneResidencia() == null ? this.paciente.getUsuario().getTelefoneResidencia() : usuarioSelected.getTelefoneResidencia());
        this.paciente.setUsuario(usuarioSelected);
        consulta.setPaciente(this.paciente);
        btnNovoUsuario = true;

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
    }

    public void clearSearchFields() {
        btnNovoUsuario = true;
        novoUsuario = false;
        paciente = newPaciente();
        clearCollection(usuarioList);
        this.consulta.setPaciente(null);
        this.procedimento = null;
        this.consulta.setConvenio(null);
        this.tipoProcedimento = null;
        clearCollection(tipoProcedimentoList);
        clearCollection(procedimentoList);
        usuarioSelected = null;
    }

    public void registerNewUser() {
        if (paciente.getUsuario().getBirthday() == null || paciente.getUsuario().getNome() == null) {
            FacesUtils.addErrorMessageNoBundle("Para cadastrar um paciente os campos Nome e Data de nascimento são obrigatórios", false);
            return;
        }
        novoUsuario = true;
        paciente.getUsuario().setNome(WordUtils.capitalizeFully(paciente.getUsuario().getNome()));
        clearCollection(usuarioList);
    }

    public void loadTipoDeProcedimentosDoConvenio() {
        tipoProcedimento = null;
        procedimento = null;
        tipoProcedimentoList = procedimentoConvenioList.stream()
                .filter(procedimentoConvenio -> procedimentoConvenio.getConvenio().equals(this.consulta.getConvenio()))
                .map(ProcedimentoConvenio::getProcedimento).map(Procedimento::getTipo)
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(TipoProcedimento::getId))),
                        ArrayList::new));

        if (tipoProcedimentoList.size() == 1) {
            tipoProcedimento = tipoProcedimentoList.get(0);
            loadProcedimentosBaseadosNoTipoDoProcedimentoDoConvenio();
        }
    }

    public void loadProcedimentosBaseadosNoTipoDoProcedimentoDoConvenio() {
        clearCombosHorariosSessao();
        clearCollection(agendaHorariosSessaoList);
        procedimentoList = procedimentoConvenioList.stream()
                .filter(procedimentoConvenio -> procedimentoConvenio.getProcedimento().getTipo().equals(tipoProcedimento) && procedimentoConvenio.getConvenio().equals(this.consulta.getConvenio()))
                .map(ProcedimentoConvenio::getProcedimento)
                .collect(Collectors.toList());
        if (procedimentoList.size() == 1) {
            procedimento = procedimentoList.get(0);
            loadProcedimentoEspecialista();
        }
    }

    public void onChangeAgendaDialog() {
        agendaHorariosModalList = AgendamentoRules.searchAgendaHorario(consultorioId, especialistaId, agendaSessao);
    }

    public void adicionarSessao() {
        agendaList.remove(agendaSessao); // Só pode ter uma sessão por dia, por isso remover agendaSessão da agendaList
        agendaHorariosSessaoList.add(agendaHorarioSessao);
        agendaHorariosSessaoList.sort(Comparator.comparing(SearchDTO::getDiaInicio));
        clearCombosHorariosSessao();
    }

    public void removerSessao(SearchDTO agendaHorarioSessao) {
        agendaList.add(agendaHorarioSessao.getAgenda());
        agendaHorariosSessaoList.remove(agendaHorarioSessao);
        agendaList.sort(Comparator.comparing(Agenda::getDataInicio));
        agendaSessao = null;
    }

    @Transactional
    public String save() throws InterruptedException, ExecutionException, TimeoutException {
        if (consulta.getConvenio() == null) {
            FacesUtils.addErrorMessageNoBundle("Selecione um Convênio", false);
        }
        if (consulta.getPaciente() == null && !novoUsuario) {
            FacesUtils.addErrorMessageNoBundle("Selecione um paciente", false);
            return null;
        }
        List<Consulta> consultaList = new ArrayList<>(agendaHorariosSessaoList.size() + 1);

        Origem o = new Origem();
        o.setId(1);
        consulta.setOrigem(o);
        consulta.setComentario("");
        consultaList.add(consulta);
        agendaHorariosSessaoList.forEach(searchDTO -> {
            Consulta consultaClone = SerializationUtils.clone(this.consulta);
            consultaClone.setOrigem(o);
            consultaClone.setHoraInicio(searchDTO.getDiaInicio());
            consultaClone.setHoraFinal(searchDTO.getDiaFim());
            consultaClone.setAgenda(searchDTO.getAgenda());
            consultaClone.setComentario("");
            consultaList.add(consultaClone);
        });
        ConsultaService.create(buildConsultaDTO(consultaList));
        return "/pages/agendamento/" + prepareBackURL();
    }
    private String prepareBackURL() {
        return new StringBuilder()
                .append("agendamento?consultorioId=")
                .append(consultorioId)
                .append("&especialidadeId=")
                .append(especialidadeId)
                .append("&especialistaId=")
                .append(especialistaId)
                .append("&dataBuscaInicio=")
                .append(Functions.formateDateToStringDDMMYYYY(dataBuscaInicio))
                .append("&dataBuscaFim=")
                .append(Functions.formateDateToStringDDMMYYYY(dataBuscaFim))
                .append("&diaHoraInicio=")
                .append(Functions.formateDateToStringYYYYMMDDHHMMSS(diaHoraInicio))
                .append("&diaHoraFim=")
                .append(Functions.formateDateToStringYYYYMMDDHHMMSS(diaHoraFim))
                .append("faces-redirect=true")
                .toString();
    }
    private ConsultaDTO buildConsultaDTO(List<Consulta> consultaList) {
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setProcedimentoEspecialista(procedimentoEspecialista);
        consultaDTO.setTipoProcedimento(tipoProcedimento);
        consultaDTO.setUnicaGuiaSessao(unicaGuiaSessao);
        consultaDTO.setConsultaRetorno(consultaRetorno);
        consultaDTO.setConsultas(consultaList);

        if(paciente.getUsuario().getCelular() != null){
            String celular = Functions.getMeMyNumber(paciente.getUsuario().getCelular());
            paciente.getUsuario().setCelular(celular);
        }

        consultaDTO.setPaciente(paciente);
        consultaDTO.setConvenio(consulta.getConvenio());
        return consultaDTO;
    }

    public void loadProcedimentoEspecialista() {
        this.procedimentoEspecialista = procedimentoEspecialistaList.stream()
                .filter(procedimentoEspecialista ->
                        procedimentoEspecialista.getLiberado()==true &&
                                procedimentoEspecialista.getEspecialista().equals(especialista) &&
                                procedimentoEspecialista.getProcedimentoConvenio().getConvenio().equals(consulta.getConvenio()) &&
                                procedimentoEspecialista.getProcedimentoConvenio().getConsultorio().equals(consultorio))
                .findFirst().orElse(null);
    }

    private void clearCombosHorariosSessao() {
        agendaHorarioSessao = null;
        agendaSessao = null;
        clearCollection(agendaHorariosModalList);
    }

    private void prepareConsultaObject() throws InterruptedException, ExecutionException, TimeoutException {
        consulta.setAgenda(CompletableFuture.supplyAsync(() -> AgendaService.getAgenda(agendaId), executor).get(10, TimeUnit.SECONDS));
        consulta.setHoraInicio(diaHoraInicio);
        consulta.setHoraFinal(diaHoraFim);
        consulta.setPerfil(loginBean.getPerfil());
        consulta.setCreatedAt(new Date());
        consulta.setStatus(new Status(StatusConsultaEnum.APROVADA.getId()));
        consulta.setPerfil(loginBean.getPerfil());
        consulta.setNotificado(false);

        //Colocar o status da consulta
    }

    //Ao invés de ir na API os dados são retirados da sessão
    private void extractInformationFromLogin() {
        Map<Consultorio, List<EspecialistaEspecialidade>> consultorioEspecialistaEspecialidadeMap = loginBean.getConsultorioEspecialistaEspecialidadeMap();
        for (Map.Entry<Consultorio, List<EspecialistaEspecialidade>> entry : consultorioEspecialistaEspecialidadeMap.entrySet()) {
            if (entry.getKey().getId().equals(consultorioId)) {
                this.consultorio = entry.getKey();
                this.especialista = entry.getValue().stream().map(EspecialistaEspecialidade::getEspecialista).filter(especialista -> especialista.getId().equals(especialistaId)).findAny().orElse(null);
                this.especialidadeList = consultorioEspecialistaEspecialidadeMap.get(consultorio)
                        .stream()
                        .filter(especialistaEspecialidade -> especialistaEspecialidade.getEspecialista().equals(especialista))
                        .map(EspecialistaEspecialidade::getEspecialidade)
                        .collect(Collectors.toList());
                this.consulta.setEspecialidade(this.especialidadeId == 0 ? null : new Especialidade(especialidadeId));
                break;
            }
        }
    }

    private Paciente newPaciente() {
        Logradouro logradouro = Logradouro.newBuilder().id(1).build();
        return Paciente
                .newBuilder()
                .id(0)
                .consultorio(consultorio)
                .usuario(Usuario.newBuilder()
                        .id(0)
                        .logradouro(logradouro)
                        .build())
                .build();
    }

    public boolean isNovoUsuario() {
        return novoUsuario;
    }

    public void setNovoUsuario(boolean novoUsuario) {
        this.novoUsuario = novoUsuario;
    }

    public boolean isConsultaRetorno() {
        return consultaRetorno;
    }

    public void setConsultaRetorno(boolean consultaRetorno) {
        this.consultaRetorno = consultaRetorno;
    }

    public TipoProcedimento getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public List<TipoProcedimento> getTipoProcedimentoList() {
        return tipoProcedimentoList;
    }

    public void setTipoProcedimentoList(List<TipoProcedimento> tipoProcedimentoList) {this.tipoProcedimentoList = tipoProcedimentoList;}

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public boolean isUnicaGuiaSessao() {
        return unicaGuiaSessao;
    }

    public void setUnicaGuiaSessao(boolean unicaGuiaSessao) {
        this.unicaGuiaSessao = unicaGuiaSessao;
    }

    public List<SearchDTO> getAgendaHorariosSessaoList() {
        return agendaHorariosSessaoList;
    }

    public void setAgendaHorariosSessaoList(List<SearchDTO> agendaHorariosSessaoList) {this.agendaHorariosSessaoList = agendaHorariosSessaoList;}

    public List<SearchDTO> getAgendaHorariosModalList() {
        return agendaHorariosModalList;
    }

    public void setAgendaHorariosModalList(List<SearchDTO> agendaHorariosModalList) {this.agendaHorariosModalList = agendaHorariosModalList;}

    public SearchDTO getAgendaHorarioSessao() {
        return agendaHorarioSessao;
    }

    public void setAgendaHorarioSessao(SearchDTO agendaHorarioSessao) {this.agendaHorarioSessao = agendaHorarioSessao;}

    public Agenda getAgendaSessao() {
        return agendaSessao;
    }

    public void setAgendaSessao(Agenda agendaSessao) {
        this.agendaSessao = agendaSessao;
    }

    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public List<Procedimento> getProcedimentoList() {
        return procedimentoList;
    }

    public void setProcedimentoList(List<Procedimento> procedimentoList) {
        this.procedimentoList = procedimentoList;
    }

    public Set<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(Set<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    public List<Especialidade> getEspecialidadeList() {
        return especialidadeList;
    }

    public void setEspecialidadeList(List<Especialidade> especialidadeList) {this.especialidadeList = especialidadeList;}

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
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

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getBackURL() {
        return backURL;
    }

    public void setBackURL(String backURL) {
        this.backURL = backURL;
    }

    public int getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(int agendaId) {
        this.agendaId = agendaId;
    }

    public int getConsultorioId() {
        return consultorioId;
    }

    public void setConsultorioId(int consultorioId) {
        this.consultorioId = consultorioId;
    }

    public int getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(int especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public Date getDataBuscaInicio() {
        return dataBuscaInicio;
    }

    public void setDataBuscaInicio(Date dataBuscaInicio) {
        this.dataBuscaInicio = dataBuscaInicio;
    }

    public Date getDataBuscaFim() {
        return dataBuscaFim;
    }

    public void setDataBuscaFim(Date dataBuscaFim) {
        this.dataBuscaFim = dataBuscaFim;
    }

    public Date getDiaHoraInicio() {
        return diaHoraInicio;
    }

    public String getDataSocketFormat() {
        return dataSocketFormat;
    }

    public void setDataSocketFormat(String dataSocketFormat) {
        this.dataSocketFormat = dataSocketFormat;
    }

    public void setDiaHoraInicio(Date diaHoraInicio) {
        this.diaHoraInicio = diaHoraInicio;
    }

    public Date getDiaHoraFim() {
        return diaHoraFim;
    }

    public void setDiaHoraFim(Date diaHoraFim) {
        this.diaHoraFim = diaHoraFim;
    }

    public boolean isBtnNovoUsuario() {return btnNovoUsuario;}

    public void setBtnNovoUsuario(boolean btnNovoUsuario) {this.btnNovoUsuario = btnNovoUsuario;}
}
