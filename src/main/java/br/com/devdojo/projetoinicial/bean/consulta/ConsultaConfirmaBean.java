package br.com.devdojo.projetoinicial.bean.consulta;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.app.ApplicationBean;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Origem;
import br.com.devdojo.projetoinicial.persistence.model.Status;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class ConsultaConfirmaBean implements Serializable {

    private final LoginBean loginBean;
    private String hashConsulta;
    private Consulta consultaConfirma;
    private String dataHoje;
    private final ApplicationBean appBean;
    private boolean regularShow = false;
    private boolean confirmShow = false;
    private boolean cancelShow = false;
    private boolean noneShow = false;

    @Inject
    public ConsultaConfirmaBean(LoginBean loginBean, ApplicationBean appBean) {
        this.loginBean = loginBean;
        this.appBean = appBean;
    }

    public void init() {
        consultaConfirma = ConsultaService.searchHash(hashConsulta);

        if (consultaConfirma.getStatus().getId() == StatusConsultaEnum.CONFIRMADO.getId()) {
            confirmShow = true;
        } else if (consultaConfirma.getStatus().getId() == StatusConsultaEnum.CANCELADA.getId()) {
            cancelShow = true;
        } else if (consultaConfirma.getStatus().getId() == StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId() || consultaConfirma.getStatus().getId() == StatusConsultaEnum.APROVADA.getId()) {
            regularShow = true;
        } else {
            noneShow = true;
        }
    }

    public void confirmarConsulta() throws IOException, InterruptedException {
        Status statusAprovada = new Status();
        statusAprovada.setId(StatusConsultaEnum.CONFIRMADO.getId());
        statusAprovada.setNome(StatusConsultaEnum.CONFIRMADO.getLabel().toLowerCase());
        consultaConfirma.setStatus(statusAprovada);
        consultaConfirma.setOrigem(Origem.newBuilder().id(3).build());
        ConsultaService.update(consultaConfirma);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String finalUrl = (((HttpServletRequest) ec.getRequest()).getRequestURI() + "?hashConsulta=" + hashConsulta);
        TimeUnit.SECONDS.sleep(1);
        ec.redirect(finalUrl);
    }

    public void cancelarConsulta() throws IOException, InterruptedException {
        Status statusCancelada = new Status();
        statusCancelada.setId(StatusConsultaEnum.CANCELADA.getId());
        statusCancelada.setNome(StatusConsultaEnum.CANCELADA.getLabel().toLowerCase());
        consultaConfirma.setStatus(statusCancelada);
        consultaConfirma.setOrigem(Origem.newBuilder().id(3).build());
        ConsultaService.update(consultaConfirma);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String finalUrl = (((HttpServletRequest) ec.getRequest()).getRequestURI() + "?hashConsulta=" + hashConsulta);
        TimeUnit.SECONDS.sleep(1);
        ec.redirect(finalUrl);
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        return "success?faces-redirect=true";
    }

    public Consulta getConsultaConfirma() {
        return consultaConfirma;
    }

    public void setConsultaConfirma(Consulta consultaConfirma) {
        this.consultaConfirma = consultaConfirma;
    }

    public String getHashConsulta() {
        return hashConsulta;
    }

    public void setHashConsulta(String hashConsulta) {
        this.hashConsulta = hashConsulta;
    }

    public boolean isRegularShow() {
        return regularShow;
    }

    public void setRegularShow(boolean regularShow) {
        this.regularShow = regularShow;
    }

    public boolean isConfirmShow() {
        return confirmShow;
    }

    public void setConfirmShow(boolean confirmShow) {
        this.confirmShow = confirmShow;
    }

    public boolean isCancelShow() {
        return cancelShow;
    }

    public void setCancelShow(boolean cancelShow) {
        this.cancelShow = cancelShow;
    }

    public boolean isNoneShow() {
        return noneShow;
    }

    public void setNoneShow(boolean noneShow) {
        this.noneShow = noneShow;
    }
}
