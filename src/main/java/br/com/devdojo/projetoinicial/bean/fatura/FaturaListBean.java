package br.com.devdojo.projetoinicial.bean.fatura;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Fatura;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturaEnum;
import br.com.devdojo.projetoinicial.service.fatura.FaturaService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Andre Ribeiro on 2/22/2017.
 */
@Named
@ViewScoped
public class FaturaListBean implements Serializable {
    private List<Fatura> faturaList;
    private StatusFaturaEnum statusFaturaEnum = StatusFaturaEnum.ABERTO;
    private Date inicio;
    private Date fim;
    private int statusFaturaParam;
    private final LoginBean loginBean;

    @Inject
    public FaturaListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }


    public void init() {
        statusFaturaEnum = statusFaturaParam == 0 ? statusFaturaEnum : StatusFaturaEnum.getEnumByStatusId(statusFaturaParam);
        search();
    }

    public void search() {
        faturaList = FaturaService.getAllFaturasByStatus(loginBean.getPerfil().getConsultorio().getId(), statusFaturaEnum.getStatus());
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

    public void setStatusFaturaEnum(StatusFaturaEnum statusFaturaEnum) {
        this.statusFaturaEnum = statusFaturaEnum;
    }
}
