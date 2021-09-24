package br.com.devdojo.projetoinicial.bean.dashboard;

import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class ChartDemoView implements Serializable {

    private BarChartModel barModel;

    private List<Consulta> listaConsultas;
    private List<String> month;

    private Date periodoInicio;
    private Date periodoFim;

    private String inicio;
    private String fim;

    private int consultorioId;

    private int qtdConsultasAgendadas;
    private int qtdConsultasRealizadas;
    private int qtdConsultasNaoRealizadas;
    private int qtdConsultasCanceladas;
    private int qtdConsultasNaoConfirmadas;
    private int qtdConsultasReagendadas;

    @PostConstruct
    public void init(int consultorio) {

        this.consultorioId = consultorio;

        LocalDate now = LocalDate.now().withDayOfMonth(1);
        LocalDate nowLocalDate = inicio == null ? LocalDate.now().withDayOfMonth(now.lengthOfMonth()) : Functions.dateStringDDMMYYYToLocalDate(inicio);
        LocalDate sixMonthAgoLocalData = fim == null ? LocalDate.now().minusMonths(6).withDayOfMonth(1) : Functions.dateStringDDMMYYYToLocalDate(fim);

        periodoInicio = Functions.localDateToDate(sixMonthAgoLocalData);
        periodoFim = Functions.localDateToDate(nowLocalDate);

        listaConsultas = new ArrayList<>();
        month = new ArrayList<>();

        listaConsultas = ConsultaService.searchConsultasByStatus(consultorio, periodoInicio, periodoFim,
                Functions.getStatusConsultas());

        qtdConsultasAgendadas = Functions.searchQtdConsultasByStatus(listaConsultas, StatusConsultaEnum.APROVADA.getId());
        qtdConsultasRealizadas = Functions.searchQtdConsultasByStatus(listaConsultas, StatusConsultaEnum.ATENDIDO.getId());
        qtdConsultasCanceladas = Functions.searchQtdConsultasByStatus(listaConsultas, StatusConsultaEnum.CANCELADA.getId());
        qtdConsultasReagendadas = Functions.searchQtdConsultasByStatus(listaConsultas, StatusConsultaEnum.REMARCADO.getId());
        qtdConsultasNaoRealizadas = qtdConsultasAgendadas - qtdConsultasRealizadas - qtdConsultasCanceladas;

        //buildListMonth(periodoInicio.getMonth(), periodoFim.getMonth());

        /*** Adicionar os outros status pata fazer parte da contagem.... ***/

        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();

        /*boys.setLabel("Consultas Agendadas");

        boys.set(this.month.get(1), qtdConsultasAgendadas);
        boys.set(this.month.get(2), qtdConsultasAgendadas);
        boys.set(this.month.get(3), qtdConsultasAgendadas);
        boys.set(this.month.get(4), qtdConsultasAgendadas);
        boys.set(this.month.get(5), qtdConsultasAgendadas);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Consultas não Realizadas");
        girls.set(this.month.get(1), qtdConsultasNaoRealizadas);
        girls.set(this.month.get(2), qtdConsultasNaoRealizadas);
        girls.set(this.month.get(3), qtdConsultasRealizadas);
        girls.set(this.month.get(4), qtdConsultasRealizadas);
        girls.set(this.month.get(5), qtdConsultasReagendadas);
        */

        boys.setLabel("Consultas Agendadas");

        boys.set("Julho", qtdConsultasAgendadas);
        boys.set("Agosto", qtdConsultasAgendadas);
        boys.set("Setembro", qtdConsultasAgendadas);
        boys.set("Outubro", qtdConsultasAgendadas);
        boys.set("Novembro", qtdConsultasAgendadas);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Consultas não Realizadas");
        girls.set("Julho", qtdConsultasNaoRealizadas);
        girls.set("Agosto", qtdConsultasNaoRealizadas);
        girls.set("Setembro", qtdConsultasRealizadas);
        girls.set("Outubro", qtdConsultasRealizadas);
        girls.set("Novembro", qtdConsultasReagendadas);

        model.addSeries(boys);
        model.addSeries(girls);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Ultimos 6 mêses");

        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("");
        yAxis.setMin(0);
        yAxis.setMax(200);

        barModel.setExtender("skinBar");
    }

    private void buildListMonth(int dataInicio, int dataFim) {
        int inicio = dataInicio + 1;
        int fim = dataFim + 1;

        while (inicio <= fim) {
            this.month.add(Functions.convertIntToStringDate(inicio));
            dataInicio++;
        }
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Date getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(Date periodoFim) {
        this.periodoFim = periodoFim;
    }

    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }

    public void setListaConsultas(List<Consulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    public int getQtdConsultasAgendadas() {
        return qtdConsultasAgendadas;
    }

    public void setQtdConsultasAgendadas(int qtdConsultasAgendadas) {
        this.qtdConsultasAgendadas = qtdConsultasAgendadas;
    }

    public int getQtdConsultasRealizadas() {
        return qtdConsultasRealizadas;
    }

    public void setQtdConsultasRealizadas(int qtdConsultasRealizadas) {
        this.qtdConsultasRealizadas = qtdConsultasRealizadas;
    }

    public int getQtdConsultasCanceladas() {
        return qtdConsultasCanceladas;
    }

    public void setQtdConsultasCanceladas(int qtdConsultasCanceladas) {
        this.qtdConsultasCanceladas = qtdConsultasCanceladas;
    }

    public int getQtdConsultasNaoConfirmadas() {
        return qtdConsultasNaoConfirmadas;
    }

    public void setQtdConsultasNaoConfirmadas(int qtdConsultasNaoConfirmadas) {
        this.qtdConsultasNaoConfirmadas = qtdConsultasNaoConfirmadas;
    }

    public int getQtdConsultasNaoRealizadas() {
        return qtdConsultasNaoRealizadas;
    }

    public void setQtdConsultasNaoRealizadas(int qtdConsultasNaoRealizadas) {
        this.qtdConsultasNaoRealizadas = qtdConsultasNaoRealizadas;
    }

    public int getQtdConsultasReagendadas() {
        return qtdConsultasReagendadas;
    }

    public void setQtdConsultasReagendadas(int qtdConsultasReagendadas) {
        this.qtdConsultasReagendadas = qtdConsultasReagendadas;
    }

    public int getConsultorioId() {
        return consultorioId;
    }

    public void setConsultorioId(int consultorioId) {
        this.consultorioId = consultorioId;
    }

    public List<String> getMonth() {
        return month;
    }

    public void setMonth(List<String> month) {
        this.month = month;
    }
}