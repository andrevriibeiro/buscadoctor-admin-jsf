package br.com.devdojo.projetoinicial.bean.financeiro;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.FaturaProcedimento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturaEnum;
import br.com.devdojo.projetoinicial.service.faturaprocedimento.FaturaProcedimentoService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.primefaces.context.RequestContext;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andre Ribeiro 4/11/2017.
 */
@Named
@ViewScoped
public class ContasPagarBean implements Serializable {

    private final LoginBean loginBean;
    private StatusFaturaEnum statusFaturaEnum = StatusFaturaEnum.ABERTO;
    private Especialista especialista;
    private Date inicio;
    private Date fim;
    private List<Especialista> especialistaList;
    private List<FaturaProcedimento> faturaProcedimentosList;
    private List<String> convenioIdListSelected = new ArrayList<>();
    private String printUrl;
    private double valorTotal;

    private int statusFaturaParam;


    @Inject
    public ContasPagarBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        especialistaList = new ArrayList<>(loginBean.getEspecialistaEspecialidadeMap().keySet());
        statusFaturaEnum = statusFaturaParam == 0 ? statusFaturaEnum : StatusFaturaEnum.getEnumByStatusId(statusFaturaParam);
    }

    public void search() {
        faturaProcedimentosList = FaturaProcedimentoService.getFaturasContasPagar(loginBean.getPerfil().getConsultorio().getId(),
                statusFaturaEnum.getStatus(), convenioIdListSelected, especialista.getId(), inicio, fim);
        calculeTotalFaturas();
        printUrl = "contasPagarPrint.xhtml?convenios=" + convenioIdListSelected.stream().collect(Collectors.joining(",")) + "&status=" + statusFaturaEnum.getStatus() + "&inicio=" + Functions.formateDateToStringDDMMYYYY(inicio) + "&fim=" + Functions.formateDateToStringDDMMYYYY(fim) + "&especialistaId=" + especialista.getId() + "&faces-redirect=true";
    }

    public void calculeTotalFaturas() {
        valorTotal = 0;
        valorTotal = faturaProcedimentosList.stream().mapToDouble((f -> f.getFatura().getValorpago())).sum();
    }

    @Transactional
    public void imprimirContasPagar() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("window.open('"+printUrl+"', '_newtab')");
    }

    public List<FaturaProcedimento> getFaturaProcedimentosList() {return faturaProcedimentosList;}

    public void setFaturaProcedimentosList(List<FaturaProcedimento> faturaProcedimentosList) {this.faturaProcedimentosList = faturaProcedimentosList;}

    public Date getInicio() {return inicio;}

    public void setInicio(Date inicio) {this.inicio = inicio;}

    public Date getFim() {return fim;}

    public void setFim(Date fim) {this.fim = fim;}

    public LoginBean getLoginBean() {return loginBean;}

    public List<String> getConvenioIdListSelected() {return convenioIdListSelected;}

    public void setConvenioIdListSelected(List<String> convenioIdListSelected) {this.convenioIdListSelected = convenioIdListSelected;}

    public Especialista getEspecialista() {return especialista;}

    public void setEspecialista(Especialista especialista) {this.especialista = especialista;}

    public List<Especialista> getEspecialistaList() {return especialistaList;}

    public void setEspecialistaList(List<Especialista> especialistaList) {this.especialistaList = especialistaList;}

    public double getValorTotal() {return valorTotal;}

    public String getPrintUrl() {
        return printUrl;
    }

    public void setPrintUrl(String printUrl) {
        this.printUrl = printUrl;
    }

    public void setValorTotal(double valorTotal) {this.valorTotal = valorTotal;}

    public StatusFaturaEnum getStatusFaturaEnum() {return statusFaturaEnum;}

    public void setStatusFaturaEnum(StatusFaturaEnum statusFaturaEnum) {this.statusFaturaEnum = statusFaturaEnum;}

}
