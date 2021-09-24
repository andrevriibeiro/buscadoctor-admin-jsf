package br.com.devdojo.projetoinicial.bean.dashboard;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.dto.DashboardDTO;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andre Ribeiro on 24/09/17.
 */
@Named
@ViewScoped
public class DashboardConsultorioBean implements Serializable {

    private final LoginBean loginBean;

    private DashboardDTO dashboardDTO;

    private List<Consulta> listaConsultas;
    private List<SearchDTO> listHorarios;

    private Date periodoInicio;
    private Date periodoFim;

    private String inicio;
    private String fim;

    private int consultorioId;


    @Inject
    public DashboardConsultorioBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @PostConstruct
    public void init() {
        this.consultorioId = loginBean.getPerfil().getConsultorio().getId();

        LocalDate localDateInicio = inicio == null ? LocalDate.now().withDayOfMonth(1) : Functions.dateStringDDMMYYYToLocalDate(inicio);
        LocalDate localDateFim = fim == null ? LocalDate.now().withDayOfMonth(localDateInicio.lengthOfMonth()) : Functions.dateStringDDMMYYYToLocalDate(fim);
        periodoInicio = Functions.localDateToDate(localDateInicio);
        periodoFim = Functions.localDateToDate(localDateFim);

        listaConsultas = new ArrayList<>();

        listaConsultas = ConsultaService.searchConsultasByStatus(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim, Functions.getStatusConsultas());

        listHorarios = ConsultaService.searchHorarios2(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim);

        this.dashboardDTO = new DashboardDTO(listaConsultas, listHorarios);
        this.dashboardDTO.loadQtdConsultasByStatus();

    }

    @Transactional
    public void buscar() {
        this.listaConsultas = ConsultaService.searchConsultasByStatus(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim, Functions.getStatusConsultas());

        this.listHorarios = ConsultaService.searchHorarios2(loginBean.getPerfil().getConsultorio().getId(),
                periodoInicio, periodoFim);

        this.dashboardDTO = new DashboardDTO(listaConsultas, listHorarios);
        this.dashboardDTO.loadQtdConsultasByStatus();

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
    }

    public DashboardDTO getDashboardDTO() {return dashboardDTO;}

    public void setDashboardDTO(DashboardDTO dashboardDTO) {this.dashboardDTO = dashboardDTO;}

    private void loadGraphsIndicators(List<Consulta> consultaList){}

    public Date getPeriodoInicio() {return periodoInicio;}

    public void setPeriodoInicio(Date periodoInicio) {this.periodoInicio = periodoInicio;}

    public Date getPeriodoFim() {return periodoFim;}

    public void setPeriodoFim(Date periodoFim) {this.periodoFim = periodoFim;}

    public List<Consulta> getListaConsultas() {return listaConsultas;}

    public List<SearchDTO> getListHorarios() {return listHorarios;}

    public void setListHorarios(List<SearchDTO> listHorarios) {this.listHorarios = listHorarios;}

    public void setListaConsultas(List<Consulta> listaConsultas) {this.listaConsultas = listaConsultas;}

    public int getConsultorioId() {return consultorioId;}

    public void setConsultorioId(int consultorioId) {this.consultorioId = consultorioId;}
}
