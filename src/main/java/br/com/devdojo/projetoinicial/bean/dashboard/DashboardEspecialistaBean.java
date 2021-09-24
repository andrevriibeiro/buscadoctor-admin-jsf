package br.com.devdojo.projetoinicial.bean.dashboard;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaEspecialidade;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DashboardModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andre Ribeiro on 26/10/17.
 */
@Named
@ViewScoped
public class DashboardEspecialistaBean implements Serializable {

    private final LoginBean loginBean;

    private DashboardModel model;

    private Set<Especialista> especialistaSet;
    private Especialista especialista;

        private Consultorio consultorio;

    private List<Consulta> listaConsultas;
    private List<SearchDTO> listHorarios;

    private Date periodoInicio;
    private Date periodoFim;

    private String inicio;
    private String fim;

    private float percentConsultasAgendadas;
    private float percentConsultasRealizadas;
    private float percentConsultasCanceladas;
    private float percentConsultasNaoConfirmada;

    private int qtdConsultasCanceladas;
    private int qtdConsultasAtendido;
    private int qtdConsultasDisponiveis;
    private int qtdConsultasConfirmadas;
    private int qtdConsultasPendenteAprovaçao;
    private int qtdConsultasNaoCompareceu;
    private int qtdConsultasPendenteCancelamento;
    private int qtdConsultasPendenterConfirmacao;
    private int qtdConsultasAgendadas;
    private int qtdConsultasRealizadas;
    private int qtdConsultasNaoConfirmadas;

    @Inject
    public DashboardEspecialistaBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @PostConstruct
    public void init() {

        consultorio = loginBean.getPerfil().getConsultorio();
        loadComboEspecialistas();

        /*
        LocalDate localDateInicio = inicio == null ? LocalDate.now().withDayOfMonth(1) : Functions.dateStringDDMMYYYToLocalDate(inicio);
        LocalDate localDateFim = fim == null ? LocalDate.now().withDayOfMonth(localDateInicio.lengthOfMonth()) : Functions.dateStringDDMMYYYToLocalDate(fim);
        periodoInicio = Functions.localDateToDate(localDateInicio);
        periodoFim = Functions.localDateToDate(localDateFim);

        listaConsultas = new ArrayList<>();

        listaConsultas = ConsultaService.searchConsultasByStatus(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim, Functions.getStatusConsultas());

        listHorarios = ConsultaService.searchHorarios2(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim);

        loadQtdConsultasByStatus();
        //loadPercentForCardIndicators();

        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();

        column1.addWidget("sports");
        column1.addWidget("finance");

        column2.addWidget("lifestyle");
        column2.addWidget("weather");

        column3.addWidget("politics");

        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);*/
    }

    @Transactional
    public void buscar() {
        this.listaConsultas = ConsultaService.searchConsultasByStatus(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim, Functions.getStatusConsultas());

        this.listHorarios = ConsultaService.searchHorarios2(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim);

        //loadQtdConsultasByStatus();
        //loadPercentForCardIndicators();

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
    }

    /**
     * Esse metodo eh responsavel por setar as quantidades de consultas por status
     *
     * */
    /*private void loadQtdConsultasByStatus(){
        this.qtdConsultasDisponiveis = searchQtdConsultasDisponiveis();
        this.qtdConsultasConfirmadas = searchQtdConsultasByStatus(StatusConsultaEnum.CONFIRMADO.getId());
        this.qtdConsultasAgendadas = searchQtdConsultasByStatus(StatusConsultaEnum.APROVADA.getId());
        this.qtdConsultasAgendadas = qtdConsultasAgendadas + qtdConsultasConfirmadas;
        this.qtdConsultasRealizadas = searchQtdConsultasByStatus(StatusConsultaEnum.ATENDIDO.getId());
        this.qtdConsultasCanceladas = searchQtdConsultasByStatus(StatusConsultaEnum.CANCELADA.getId());
        this.qtdConsultasPendenteAprovaçao = searchQtdConsultasByStatus(StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId());
        this.qtdConsultasPendenteCancelamento = searchQtdConsultasByStatus(StatusConsultaEnum.PENDENTE_CANCELAMENTO.getId());
        this.qtdConsultasPendenterConfirmacao = searchQtdConsultasByStatus(StatusConsultaEnum.PENDENTE_CONFIRMACAO.getId());

        this.qtdConsultasNaoConfirmadas = this.qtdConsultasAgendadas - this.qtdConsultasRealizadas -
                this.qtdConsultasCanceladas;

        this.qtdConsultasDisponiveis = this.qtdConsultasDisponiveis + this.qtdConsultasPendenteAprovaçao +
                this.qtdConsultasPendenteCancelamento + this.qtdConsultasPendenteAprovaçao;
    }*/

    /**
     * Esse metodo eh responsavel por carregar as porcentagens de consultas apresentadas
     * nos indicator cards do dashboard
     *
     * */
    private void loadPercentForCardIndicators(){
        if(this.qtdConsultasDisponiveis!=0 && this.qtdConsultasAgendadas!=0){
            this.percentConsultasAgendadas = (this.qtdConsultasAgendadas * 100) / this.qtdConsultasDisponiveis;
        }

        if(this.qtdConsultasCanceladas!=0 && this.qtdConsultasAgendadas !=0){
            this.percentConsultasCanceladas = (this.qtdConsultasCanceladas * 100) / this.qtdConsultasAgendadas;
        }

        if(this.qtdConsultasNaoConfirmadas!=0 && this.qtdConsultasAgendadas!=0){
            this.percentConsultasNaoConfirmada = (this.qtdConsultasNaoConfirmadas * 100) / this.qtdConsultasAgendadas;
        }

        if(this.qtdConsultasRealizadas!=0 && this.qtdConsultasAgendadas!=0){
            this.percentConsultasRealizadas = (this.qtdConsultasRealizadas * 100) / this.qtdConsultasAgendadas;
        }
    }

    private void loadGraphsIndicators(List<Consulta> consultaList){}

    /**
     * Esse metodo eh responsavel por retornar a quantidade de consultas por status
     *
     * @param status das consultas
     * return quantidade de consultas
     *
     * */
    public int searchQtdConsultasByStatus(Integer status) {
        return (int) this.listaConsultas.stream().
                filter(consulta -> consulta.getStatus().getId().equals(status))
                .count();
    }

    public int searchQtdConsultasDisponiveis(){
        return (int) this.listHorarios.stream()
                .filter(searchDTO -> searchDTO.isDisponivel())
                .count();
    }

    private void loadComboEspecialistas() {
        especialistaSet = loginBean.getConsultorioEspecialistaEspecialidadeMap().get(consultorio)
                .stream()
                .map(EspecialistaEspecialidade::getEspecialista)
                .sorted(Comparator.comparing(Especialista::getNome))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public DashboardModel getModel() {
        return model;
    }

    public Set<Especialista> getEspecialistaSet() {return especialistaSet;}

    public void setEspecialistaSet(Set<Especialista> especialistaSet) {this.especialistaSet = especialistaSet;}

    public Especialista getEspecialista() {return especialista;}

    public void setEspecialista(Especialista especialista) {this.especialista = especialista;}

    public Date getPeriodoInicio() {return periodoInicio;}

    public void setPeriodoInicio(Date periodoInicio) {this.periodoInicio = periodoInicio;}

    public Date getPeriodoFim() {return periodoFim;}

    public void setPeriodoFim(Date periodoFim) {this.periodoFim = periodoFim;}

    public List<Consulta> getListaConsultas() {return listaConsultas;}

    public List<SearchDTO> getListHorarios() {return listHorarios;}

    public void setListHorarios(List<SearchDTO> listHorarios) {this.listHorarios = listHorarios;}

    public void setListaConsultas(List<Consulta> listaConsultas) {this.listaConsultas = listaConsultas;}

    public int getQtdConsultasAgendadas() {return qtdConsultasAgendadas;}

    public void setQtdConsultasAgendadas(int qtdConsultasAgendadas) {this.qtdConsultasAgendadas = qtdConsultasAgendadas;}

    public int getQtdConsultasRealizadas() {return qtdConsultasRealizadas;}

    public void setQtdConsultasRealizadas(int qtdConsultasRealizadas) {this.qtdConsultasRealizadas = qtdConsultasRealizadas;}

    public int getQtdConsultasCanceladas() {return qtdConsultasCanceladas;}

    public void setQtdConsultasCanceladas(int qtdConsultasCanceladas) {this.qtdConsultasCanceladas = qtdConsultasCanceladas;}

    public int getQtdConsultasNaoConfirmadas() {return qtdConsultasNaoConfirmadas;}

    public void setQtdConsultasNaoConfirmadas(int qtdConsultasNaoConfirmadas) {this.qtdConsultasNaoConfirmadas = qtdConsultasNaoConfirmadas;}

    public float getPercentConsultasAgendadas() {return percentConsultasAgendadas;}

    public void setPercentConsultasAgendadas(float percentConsultasAgendadas) {this.percentConsultasAgendadas = percentConsultasAgendadas;}

    public int getQtdConsultasAtendido() {return qtdConsultasAtendido;}

    public void setQtdConsultasAtendido(int qtdConsultasAtendido) {this.qtdConsultasAtendido = qtdConsultasAtendido;}

    public int getQtdConsultasDisponiveis() {return qtdConsultasDisponiveis;}

    public void setQtdConsultasDisponiveis(int qtdConsultasDisponiveis) {this.qtdConsultasDisponiveis = qtdConsultasDisponiveis;}

    public int getQtdConsultasConfirmadas() {return qtdConsultasConfirmadas;}

    public void setQtdConsultasConfirmadas(int qtdConsultasConfirmadas) {this.qtdConsultasConfirmadas = qtdConsultasConfirmadas;}

    public int getQtdConsultasPendenteAprovaçao() {return qtdConsultasPendenteAprovaçao;}

    public void setQtdConsultasPendenteAprovaçao(int qtdConsultasPendenteAprovaçao) {this.qtdConsultasPendenteAprovaçao = qtdConsultasPendenteAprovaçao;}

    public int getQtdConsultasNaoCompareceu() {return qtdConsultasNaoCompareceu;}

    public void setQtdConsultasNaoCompareceu(int qtdConsultasNaoCompareceu) {this.qtdConsultasNaoCompareceu = qtdConsultasNaoCompareceu;}

    public int getQtdConsultasPendenteCancelamento() {return qtdConsultasPendenteCancelamento;}

    public void setQtdConsultasPendenteCancelamento(int qtdConsultasPendenteCancelamento) {this.qtdConsultasPendenteCancelamento = qtdConsultasPendenteCancelamento;}

    public int getQtdConsultasPendenterConfirmacao() {return qtdConsultasPendenterConfirmacao;}

    public void setQtdConsultasPendenterConfirmacao(int qtdConsultasPendenterConfirmacao) {this.qtdConsultasPendenterConfirmacao = qtdConsultasPendenterConfirmacao;}

    public Consultorio getConsultorio() {return consultorio;}

    public void setConsultorio(Consultorio consultorio) {this.consultorio = consultorio;}

    public double getPercentConsultasAgendada() {return percentConsultasAgendadas;}

    public void setPercentConsultasAgendada(float percentConsultasAgendada) {this.percentConsultasAgendadas = percentConsultasAgendada;}

    public float getPercentConsultasRealizadas() {return percentConsultasRealizadas;}

    public void setPercentConsultasRealizadas(float percentConsultasRealizadas) {this.percentConsultasRealizadas = percentConsultasRealizadas;}

    public float getPercentConsultasCanceladas() {return percentConsultasCanceladas;}

    public void setPercentConsultasCanceladas(float percentConsultasCanceladas) {this.percentConsultasCanceladas = percentConsultasCanceladas;}

    public float getPercentConsultasNaoConfirmada() {return percentConsultasNaoConfirmada;}

    public void setPercentConsultasNaoConfirmada(float percentConsultasNaoConfirmada) {this.percentConsultasNaoConfirmada = percentConsultasNaoConfirmada;}

}
