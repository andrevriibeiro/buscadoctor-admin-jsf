package br.com.devdojo.projetoinicial.bean.faturamento;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.FaturamentoRules;
import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturamentoEnum;
import br.com.devdojo.projetoinicial.service.faturamento.FaturamentoService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.devdojo.projetoinicial.utils.FacesUtils.addErrorMessage;
import static br.com.devdojo.projetoinicial.utils.FacesUtils.addSuccessMessage;
import static java.util.stream.Collectors.toList;

/**
 * @author Andre Ribeiro on 2/20/2017.
 */
@Named
@ViewScoped
public class FaturamentoListBean implements Serializable {
    private List<Faturamento> faturamentoList;
    private StatusFaturamentoEnum statusFaturamentoEnum = StatusFaturamentoEnum.ABERTO;
    private Set<Faturamento> faturamentosWithFaturaList = new HashSet<>(1);
    private List<Faturamento> faturamentoListSelected = new ArrayList<>();
    private List<String> convenioIdListSelected = new ArrayList<>();
    private Faturamento faturamento = new Faturamento();
    private final LoginBean loginBean;
    private String inicio;
    private String fim;
    private int statusFaturamentoParam;
    private static final Logger LOGGER = LoggerFactory.getLogger(FaturamentoListBean.class);

    @Inject
    public FaturamentoListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        statusFaturamentoEnum = StatusFaturamentoEnum.getEnumByStatusId(statusFaturamentoParam);
        LocalDate localDateInicio = inicio == null ? LocalDate.now().withDayOfMonth(1) : Functions.dateStringDDMMYYYToLocalDate(inicio);
        LocalDate localDateFim = fim == null ? LocalDate.now().withDayOfMonth(localDateInicio.lengthOfMonth()) : Functions.dateStringDDMMYYYToLocalDate(fim);
        localDateInicio = localDateInicio.minusMonths(1);
        faturamento.setInicio(Functions.localDateToDate(localDateInicio));
        faturamento.setFim(Functions.localDateToDate(localDateFim));
        search();
    }

    public void search() {
        faturamentoList = FaturamentoService.search(loginBean.getPerfil().getConsultorio().getId(), convenioIdListSelected, statusFaturamentoEnum.getStatus(), faturamento.getInicio(), faturamento.getFim());
        if (statusFaturamentoEnum.equals(StatusFaturamentoEnum.ABERTO))
            searchFaturamentosWithFatura();
    }

    private void searchFaturamentosWithFatura() {
        List<Integer> faturamentosId = faturamentoList.stream().map(Faturamento::getId).collect(toList());
        faturamentosWithFaturaList = faturamentosId.size() > 0 ? FaturamentoService.search(faturamentosId) : new HashSet<>(1);
    }

    @Transactional
    public void delete(Faturamento faturamento) {
        int statusCode = FaturamentoService.deleteFauramento(faturamento);
        if (statusCode == 200 || statusCode == 201) {
            faturamentoList.remove(faturamento);
            addSuccessMessage("successDelete", false);
            return;
        }
        addErrorMessage("errorDelete", false);
    }

    @Transactional
    public void closeFaturamento() {
        if (faturamentoListSelected.isEmpty()) {
            FacesUtils.addErrorMessageNoBundle("Nenhum faturamento selecionado", false);
            throw new IllegalArgumentException("Nenhum faturamento selecionado");
        }
        FaturamentoRules.closeFaturamento(faturamentoListSelected);
        faturamentoList.removeAll(faturamentoListSelected);
        faturamentoListSelected.clear();
//        FacesUtils.addSuccessMessage("sucessoOperacao",false);
    }

    public boolean disabledSelection(Faturamento faturamento) {

        return !faturamentosWithFaturaList.contains(faturamento) || faturamento.getDataVencimento() == null;
    }
    @Transactional
    public void onCellEdit(Faturamento faturamento) {
        try {
            FaturamentoService.update(faturamento);
            addSuccessMessage("successUpdateFaturamentoList", false);
        } catch (Exception e) {
            LOGGER.error("Erro ao editar datatable do faturamento list ", e);
            addErrorMessage("errorUpdate", false);
        }

    }

    public int getStatusFaturamentoParam() {
        return statusFaturamentoParam;
    }

    public void setStatusFaturamentoParam(int statusFaturamentoParam) {
        this.statusFaturamentoParam = statusFaturamentoParam;
    }

    public Set<Faturamento> getFaturamentosWithFaturaList() {
        return faturamentosWithFaturaList;
    }

    public void setFaturamentosWithFaturaList(Set<Faturamento> faturamentosWithFaturaList) {
        this.faturamentosWithFaturaList = faturamentosWithFaturaList;
    }

    public List<Faturamento> getFaturamentoListSelected() {
        return faturamentoListSelected;
    }

    public void setFaturamentoListSelected(List<Faturamento> faturamentoListSelected) {
        this.faturamentoListSelected = faturamentoListSelected;
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

    public Faturamento getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(Faturamento faturamento) {
        this.faturamento = faturamento;
    }

    public List<String> getConvenioIdListSelected() {
        return convenioIdListSelected;
    }

    public void setConvenioIdListSelected(List<String> convenioIdListSelected) {
        this.convenioIdListSelected = convenioIdListSelected;
    }

    public List<Faturamento> getFaturamentoList() {
        return faturamentoList;
    }

    public void setFaturamentoList(List<Faturamento> faturamentoList) {
        this.faturamentoList = faturamentoList;
    }

    public StatusFaturamentoEnum getStatusFaturamentoEnum() {
        return statusFaturamentoEnum;
    }

    public void setStatusFaturamentoEnum(StatusFaturamentoEnum statusFaturamentoEnum) {
        this.statusFaturamentoEnum = statusFaturamentoEnum;
    }
}
