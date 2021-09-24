package br.com.devdojo.projetoinicial.bean.fatura;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Fatura;
import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturaEnum;
import br.com.devdojo.projetoinicial.service.fatura.FaturaService;
import br.com.devdojo.projetoinicial.service.faturamento.FaturamentoService;
import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Andre Ribeiro on 2/22/2017.
 */
@Named
@ViewScoped
public class FaturaConvenioListBean implements Serializable {
    private List<Fatura> faturaList;
    private StatusFaturaEnum statusFaturaEnum = StatusFaturaEnum.ABERTO;
    private Date inicio;
    private int convenioId;
    private String inicioString;
    private String fimString;
    private Date fim;
    private int statusFaturaParam;
    private final LoginBean loginBean;
    private Faturamento faturamentoDisponivel;
    private List<Fatura> faturaListSelected;
    private int faturamentoDisponivelId;

    @Inject
    public FaturaConvenioListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }


    public void init() {
        faturamentoDisponivel = FaturamentoService.getById(faturamentoDisponivelId);
        LocalDate inicioLocal = Functions.dateStringDDMMYYYToLocalDate(inicioString);
        LocalDate fimLocal = Functions.dateStringDDMMYYYToLocalDate(fimString);
        inicio = Date.from(inicioLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fim = Date.from(fimLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        statusFaturaEnum = statusFaturaParam == 0 ? statusFaturaEnum : StatusFaturaEnum.getEnumByStatusId(statusFaturaParam);
        search();
    }

    public void search() {
        faturaList = FaturaService.getAllFaturasByStatusAndConvenio(loginBean.getPerfil().getConsultorio().getId(), statusFaturaEnum.getStatus(), convenioId);
    }


    /**
     * Esse metodo eh responsavel por faturar todas as faturas selecionadas pelo usuario
     * Ou seja, associar todas as faturas selecionada pelo usuario ah um faturamento.
     * */
    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {

        faturaListSelected.forEach(fatura -> {
            //faturamentoDisponivel.setValor(faturamentoDisponivel.getValor() + fatura.getValor());
            fatura.getStatusFatura().setId(StatusFaturaEnum.FECHADO.getStatus());
            fatura.setFaturamento(faturamentoDisponivel);

            //Gambiarra da API
            fatura.setCreatedAt(new Date());
            FaturaService.update(fatura);
        });

        return "editar?faturamentoId="+ this.faturamentoDisponivelId + "&status=" + this.statusFaturaParam + "&inicio=" + this.inicio + "&fim=" + this.fim + "&faces-redirect=true";
    }

    public int getStatusFaturaParam() {
        return statusFaturaParam;
    }

    public void setStatusFaturaParam(int statusFaturaParam) {
        this.statusFaturaParam = statusFaturaParam;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public int getConvenioId() {
        return convenioId;
    }

    public void setConvenioId(int convenioId) {
        this.convenioId = convenioId;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public List<Fatura> getFaturaList() {
        return faturaList;
    }

    public void setFaturaList(List<Fatura> faturaList) {
        this.faturaList = faturaList;
    }

    public StatusFaturaEnum getStatusFaturaEnum() {
        return statusFaturaEnum;
    }

    public String getInicioString() {
        return inicioString;
    }

    public void setInicioString(String inicioString) {
        this.inicioString = inicioString;
    }

    public String getFimString() {
        return fimString;
    }

    public void setFimString(String fimString) {
        this.fimString = fimString;
    }

    public void setStatusFaturaEnum(StatusFaturaEnum statusFaturaEnum) {
        this.statusFaturaEnum = statusFaturaEnum;
    }

    public Faturamento getFaturamentoDisponivel() {
        return faturamentoDisponivel;
    }

    public void setFaturamentoDisponivel(Faturamento faturamentoDisponivel) {
        this.faturamentoDisponivel = faturamentoDisponivel;
    }
    public int getFaturamentoDisponivelId() {
        return faturamentoDisponivelId;
    }

    public List<Fatura> getFaturaListSelected() {
        return faturaListSelected;
    }

    public void setFaturaListSelected(List<Fatura> faturaListSelected) {
        this.faturaListSelected = faturaListSelected;
    }

    public void setFaturamentoDisponivelId(int faturamentoDisponivelId) {
        this.faturamentoDisponivelId = faturamentoDisponivelId;
    }
}
