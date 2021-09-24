package br.com.devdojo.projetoinicial.bean.faturamento;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.domainrules.FaturamentoRules;
import br.com.devdojo.projetoinicial.persistence.model.Fatura;
import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturamentoEnum;
import br.com.devdojo.projetoinicial.service.fatura.FaturaService;
import br.com.devdojo.projetoinicial.service.faturamento.FaturamentoService;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class FaturamentoEditBean implements Serializable {
    private int faturamentoId;
    private Faturamento faturamento;
    private List<Fatura> faturaList;
    private List<Fatura> faturaListSelected = new ArrayList<>();
    private List<Fatura> faturaRemovidasList = new ArrayList<>();
    private StatusFaturamentoEnum statusFaturamentoEnum;
    //Usado para manter os parâmetros quando voltar para listagem de faturamentos
    private String inicio;
    private String fim;
    private int statusFaturamentoParam;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        faturamento =CompletableFuture.supplyAsync(() -> FaturamentoService.getById(faturamentoId), executor).get(10, TimeUnit.SECONDS);
        faturaList = CompletableFuture.supplyAsync(() -> FaturaService.getFaturaByFaturamentoId(faturamentoId), executor).get(10, TimeUnit.SECONDS);
        statusFaturamentoEnum = StatusFaturamentoEnum.getEnumByStatusId(faturamento.getFinalizado() ? 1 : 0);
        this.statusFaturamentoParam = 0;
    }

    @Transactional
    public String update() {

        FaturaService.updateFaturaDoFaturamento(faturaRemovidasList);
        FaturamentoService.update(faturamento);
        return "editar?faturamentoId=" + this.faturamentoId + "&inicio=" + this.inicio + "&fim=" + this.fim + "&status=" + this.statusFaturamentoParam + "&faces-redirect=true";
    }

    @Transactional
    public String closeFaturamento() {
        FaturamentoRules.closeFaturamento(this.faturamento);
        return "faturamento?inicio=" + this.inicio + "&fim=" + this.fim + "&faces-redirect=true";
    }

    public void removeFaturasFromFaturamento() {
        faturaListSelected.forEach(fatura -> {
            fatura.setFaturamento(null);
            faturaList.remove(fatura);
            faturaRemovidasList.add(fatura);
        });
    }

    public StatusFaturamentoEnum getStatusFaturamentoEnum() {
        return statusFaturamentoEnum;
    }

    public void setStatusFaturamentoEnum(StatusFaturamentoEnum statusFaturamentoEnum) {
        this.statusFaturamentoEnum = statusFaturamentoEnum;
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

    public int getStatusFaturamentoParam() {
        return statusFaturamentoParam;
    }

    public void setStatusFaturamentoParam(int statusFaturamentoParam) {
        this.statusFaturamentoParam = statusFaturamentoParam;
    }

    public List<Fatura> getFaturaList() {
        return faturaList;
    }

    public void setFaturaList(List<Fatura> faturaList) {
        this.faturaList = faturaList;
    }

    public List<Fatura> getFaturaListSelected() {
        return faturaListSelected;
    }

    public void setFaturaListSelected(List<Fatura> faturaListSelected) {
        this.faturaListSelected = faturaListSelected;
    }

    public Faturamento getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(Faturamento faturamento) {
        this.faturamento = faturamento;
    }

    public int getFaturamentoId() {
        return faturamentoId;
    }

    public void setFaturamentoId(int faturamentoId) {
        this.faturamentoId = faturamentoId;
    }
}
