package br.com.devdojo.projetoinicial.bean.financeiro;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.FaturaProcedimento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturaEnum;
import br.com.devdojo.projetoinicial.service.faturaprocedimento.FaturaProcedimentoService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Named
@ViewScoped
public class ContasPagarPrintBean implements Serializable {
    private final LoginBean loginBean;
    private List<FaturaProcedimento> faturaProcedimentosList;
    private double valorTotal;
    private int statusFatura;
    private int especialistaId;
    private Especialista especialista;
    private Date inicio;
    private String conveniosIdsListString;
    private Date fim;
    private StatusFaturaEnum statusFaturaEnum;

    @Inject
    public ContasPagarPrintBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        List<String> convenioIdListSelected = Arrays.asList(conveniosIdsListString.split(","));
        faturaProcedimentosList = FaturaProcedimentoService.getFaturasContasPagar(loginBean.getPerfil().getConsultorio().getId(),
                statusFatura, convenioIdListSelected, especialistaId, inicio, fim);
        calculeTotalFaturas();
        especialista = faturaProcedimentosList.get(0).getProcedimentoEspecialista().getEspecialista();
        statusFaturaEnum = statusFatura == 0 ? statusFaturaEnum : StatusFaturaEnum.getEnumByStatusId(statusFatura);
    }

    public void calculeTotalFaturas() {
        valorTotal = faturaProcedimentosList.stream().mapToDouble((f -> f.getFatura().getValorpago())).sum();
    }


    public List<FaturaProcedimento> getFaturaProcedimentosList() {
        return faturaProcedimentosList;
    }

    public void setFaturaProcedimentosList(List<FaturaProcedimento> faturaProcedimentosList) {
        this.faturaProcedimentosList = faturaProcedimentosList;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getInicio() {
        return inicio;
    }

    public int getStatusFatura() {
        return statusFatura;
    }

    public void setStatusFatura(int statusFatura) {
        this.statusFatura = statusFatura;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public StatusFaturaEnum getStatusFaturaEnum() {
        return statusFaturaEnum;
    }

    public void setStatusFaturaEnum(StatusFaturaEnum statusFaturaEnum) {
        this.statusFaturaEnum = statusFaturaEnum;
    }

    public Date getFim() {
        return fim;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public String getConveniosIdsListString() {
        return conveniosIdsListString;
    }

    public void setConveniosIdsListString(String conveniosIdsListString) {
        this.conveniosIdsListString = conveniosIdsListString;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

}
