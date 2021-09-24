package br.com.devdojo.projetoinicial.bean.faturamento;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.domainrules.FaturamentoRules;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.service.faturamento.FaturamentoService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;

import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class FaturamentoRegisBean implements Serializable {
    private List<Convenio> convenioListSelected = new ArrayList<>();
    private Set<Convenio> conveniosSamePeriod;
    private Date inicio;
    private Date fim;
    private final LoginBean loginBean;

    @Inject
    public FaturamentoRegisBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }


    public void buscarFaturamentosPeriodo() {
        if (inicio != null && fim != null)
            conveniosSamePeriod = FaturamentoRules.buscarConveniosPeriodoFaturamento(inicio, fim, loginBean.getPerfil().getConsultorio().getId(), Collections.emptySet());
    }

    public String create() throws ValidatorException {
        conveniosSamePeriod.retainAll(convenioListSelected);
        if (!conveniosSamePeriod.isEmpty()) {
            List<String> collect = conveniosSamePeriod.stream().map(Convenio::getNome).collect(Collectors.toList());
            FacesUtils.addErrorMessageNoBundle("Já existem faturamento nesse período para os seguintes planos: " + collect, false);
            return null;
        }
        if (convenioListSelected.isEmpty()) {
            FacesUtils.addErrorMessageNoBundle("Obrigatório um convênio", false);
            return null;

        }
        List<Faturamento> faturamentos = new ArrayList<>();

        Consultorio consultorio = Consultorio.newBuilder().id(loginBean.getPerfil().getConsultorio().getId()).build();
        convenioListSelected.forEach(convenio -> faturamentos.add(Faturamento.newBuilder()
                .convenio(convenio)
                .consultorio(consultorio)
                .inicio(inicio)
                .fim(fim)
                .valor(0)
                .finalizado(false).build()));

        FaturamentoService.create(faturamentos);
//        buscarFaturamentosPeriodo();
        FacesUtils.addSuccessMessage("sucessRegister", true);
        return "faturamento?faces-redirect=true";
    }

    public Set<Convenio> getConveniosSamePeriod() {
        return conveniosSamePeriod;
    }

    public void setConveniosSamePeriod(Set<Convenio> conveniosSamePeriod) {
        this.conveniosSamePeriod = conveniosSamePeriod;
    }

    public List<Convenio> getConvenioListSelected() {
        return convenioListSelected;
    }

    public void setConvenioListSelected(List<Convenio> convenioListSelected) {
        this.convenioListSelected = convenioListSelected;
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

    public void setFim(Date fim) {
        this.fim = fim;
    }
}
