package br.com.devdojo.projetoinicial.bean.fatura;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Fatura;
import br.com.devdojo.projetoinicial.persistence.model.FaturaProcedimento;
import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoEspecialista;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturaEnum;
import br.com.devdojo.projetoinicial.service.fatura.FaturaService;
import br.com.devdojo.projetoinicial.service.faturamento.FaturamentoService;
import br.com.devdojo.projetoinicial.service.faturaprocedimento.FaturaProcedimentoService;
import br.com.devdojo.projetoinicial.service.procedimentoespecialista.ProcedimentoEspecialistaService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class FaturaEditBean implements Serializable {
    private int faturaId;
    private FaturaProcedimento faturaProcedimento;
    private List<FaturaProcedimento> faturaProcedimentoList;
    private List<FaturaProcedimento> faturaProcedimentoSelectedList = new ArrayList<>();
    private List<FaturaProcedimento> faturaProcedimentoRemovidosList = new ArrayList<>();
    private List<ProcedimentoEspecialista> procedimentoEspecialistaList;
    private FaturaProcedimento newFaturaProcedimento;
    private Faturamento faturamentoDisponivel;
    private List<Faturamento> faturamentosDisponivelList = new ArrayList<>(1);
    private StatusFaturaEnum statusFaturaEnum;
    private boolean enableFields;
    private String previousPage;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final LoginBean loginBean;

    @Inject
    public FaturaEditBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }


    public void init() {

        faturaProcedimentoList = FaturaProcedimentoService.getFaturaProcedimento(faturaId);
        faturaProcedimento = !faturaProcedimentoList.isEmpty() ? faturaProcedimentoList.get(0) : new FaturaProcedimento();
        statusFaturaEnum = StatusFaturaEnum.getEnumByStatusId(faturaProcedimento.getFatura().getStatusFatura().getId());
        enableFields = statusFaturaEnum.equals(StatusFaturaEnum.GLOSADO) || statusFaturaEnum.equals(StatusFaturaEnum.ABERTO);
        configProcedimentoEspecialistaList();
        createNewFaturaProcedimento();
        searchFaturamentoAberto();
        previousPage = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");
        previousPage = previousPage != null ? ".."+previousPage.split("pages")[1] : "fatura?status="+statusFaturaEnum.getStatus();
    }

    private void configProcedimentoEspecialistaList() {
        procedimentoEspecialistaList = ProcedimentoEspecialistaService.search(faturaProcedimento.getProcedimentoEspecialista().getEspecialista().getId(),
                faturaProcedimento.getProcedimentoEspecialista().getProcedimentoConvenio().getConvenio().getId(), loginBean.getPerfil().getConsultorio().getId(), true);
        procedimentoEspecialistaList.removeAll(faturaProcedimentoList.stream().map(FaturaProcedimento::getProcedimentoEspecialista).collect(Collectors.toList()));
    }

    private void createNewFaturaProcedimento() {
        this.newFaturaProcedimento = FaturaProcedimento.newBuilder().quantidade(1).fatura(faturaProcedimento.getFatura()).consulta(faturaProcedimento.getConsulta()).build();
    }

    private void searchFaturamentoAberto() {
        if (statusFaturaEnum.equals(StatusFaturaEnum.ABERTO) || statusFaturaEnum.equals(StatusFaturaEnum.GLOSADO)) {
            faturamentosDisponivelList = FaturamentoService.search(loginBean.getPerfil().getConsultorio().getId(),
                    Collections.singletonList(faturaProcedimento.getConsulta().getConvenio().getId().toString()),
                    faturaProcedimento.getConsulta().getHoraInicio());
            faturamentoDisponivel = faturamentosDisponivelList.size() > 0 ? faturamentosDisponivelList.get(0) : null;
        }

    }

    @Transactional
    public void removeProcedimentoFromFatura() {
        //    Fatura não pode ficar sem procedimento
        if (faturaProcedimentoList.size() == faturaProcedimentoSelectedList.size() || faturaProcedimentoSelectedList.size() == 0) {
            FacesUtils.addErrorMessage(faturaProcedimentoSelectedList.size() == 0 ? "errorSelecionarUmProcedimento" : "errorUmProcedimentoNecessario", false);
            throw new IllegalArgumentException();
        }
        faturaProcedimentoSelectedList.forEach(fp -> {
            faturaProcedimentoList.remove(fp);
            faturaProcedimentoRemovidosList.add(fp);
        });
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture.supplyAsync(() -> FaturaProcedimentoService.delete(faturaProcedimentoRemovidosList), executor).get(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> FaturaProcedimentoService.update(faturaProcedimentoList), executor).get(10, TimeUnit.SECONDS);

        return "fatura?faces-redirect=true";
    }

    @Transactional
    public String update(StatusFaturaEnum statusFaturaEnum) throws InterruptedException, ExecutionException, TimeoutException {
        Fatura fatura = faturaProcedimento.getFatura();
        fatura.getStatusFatura().setId(statusFaturaEnum.getStatus());
        fatura.setFaturamento(faturamentoDisponivel);
        //Gambiarra da API
        fatura.setCreatedAt(faturaProcedimento.getConsulta().getHoraInicio());
        update();
        FaturaService.update(faturaProcedimento.getFatura());
        return "fatura?faces-redirect=true";
    }

    @Transactional
    public void addFaturaProcedimento() {
        faturaProcedimentoList.add(FaturaProcedimentoService.save(newFaturaProcedimento));
        procedimentoEspecialistaList.remove(newFaturaProcedimento.getProcedimentoEspecialista());
        createNewFaturaProcedimento();
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public List<Faturamento> getFaturamentosDisponivelList() {
        return faturamentosDisponivelList;
    }

    public void setFaturamentosDisponivelList(List<Faturamento> faturamentosDisponivelList) {
        this.faturamentosDisponivelList = faturamentosDisponivelList;
    }

    public boolean isEnableFields() {
        return enableFields;
    }

    public void setEnableFields(boolean enableFields) {
        this.enableFields = enableFields;
    }

    public StatusFaturaEnum getStatusFaturaEnum() {
        return statusFaturaEnum;
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

    public FaturaProcedimento getNewFaturaProcedimento() {
        return newFaturaProcedimento;
    }

    public void setNewFaturaProcedimento(FaturaProcedimento newFaturaProcedimento) {
        this.newFaturaProcedimento = newFaturaProcedimento;
    }

    public List<ProcedimentoEspecialista> getProcedimentoEspecialistaList() {
        return procedimentoEspecialistaList;
    }

    public void setProcedimentoEspecialistaList(List<ProcedimentoEspecialista> procedimentoEspecialistaList) {
        this.procedimentoEspecialistaList = procedimentoEspecialistaList;
    }

    public List<FaturaProcedimento> getFaturaProcedimentoSelectedList() {
        return faturaProcedimentoSelectedList;
    }

    public void setFaturaProcedimentoSelectedList(List<FaturaProcedimento> faturaProcedimentoSelectedList) {
        this.faturaProcedimentoSelectedList = faturaProcedimentoSelectedList;
    }


    public List<FaturaProcedimento> getFaturaProcedimentoList() {
        return faturaProcedimentoList;
    }

    public void setFaturaProcedimentoList(List<FaturaProcedimento> faturaProcedimentoList) {
        this.faturaProcedimentoList = faturaProcedimentoList;
    }

    public int getFaturaId() {
        return faturaId;
    }

    public void setFaturaId(int faturaId) {
        this.faturaId = faturaId;
    }

    public FaturaProcedimento getFaturaProcedimento() {
        return faturaProcedimento;
    }

    public void setFaturaProcedimento(FaturaProcedimento faturaProcedimento) {
        this.faturaProcedimento = faturaProcedimento;
    }
}
