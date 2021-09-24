package br.com.devdojo.projetoinicial.bean.consulta;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.app.ApplicationBean;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.Status;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import org.primefaces.context.RequestContext;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class ConsultaPendenteBean implements Serializable {
    private final LoginBean loginBean;
    private List<Consulta> listaConsultas;
    private boolean showStatusDialog;
    private List<Status> statusChange;
    private Status statusEscolhido;
    private Consulta consultaUpdate;
    private final ApplicationBean appBean;
    private Consultorio consultorio;


    @Inject
    public ConsultaPendenteBean(LoginBean loginBean, ApplicationBean appBean) {
        this.loginBean = loginBean;
        this.appBean = appBean;
    }

    public void init() {
        consultorio = loginBean.getPerfil().getConsultorio();
        ArrayList<Integer> status = new ArrayList<>();
        status.add(StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId());
        Date date = new Date();
        Date dateYearLate = new Date();
        dateYearLate.setYear(dateYearLate.getYear() + 1);
        listaConsultas = ConsultaService.searchConsultasByStatus(loginBean.getPerfil().getConsultorio().getId(), date, dateYearLate, status);

        StatusConsultaEnum aprovada = StatusConsultaEnum.getEnumByStatusId(3);
        Status statusAprovada = new Status();
        statusAprovada.setId(aprovada.getId());
        statusAprovada.setNome(aprovada.name().toLowerCase());

        StatusConsultaEnum cancelada = StatusConsultaEnum.getEnumByStatusId(5);
        Status statusCancelada = new Status();
        statusCancelada.setId(cancelada.getId());
        statusCancelada.setNome(cancelada.name().toLowerCase());

        statusChange = new ArrayList<>();
        statusChange.add(statusAprovada);
        statusChange.add(statusCancelada);
    }

    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }

    public boolean isShowStatusDialog() {
        return showStatusDialog;
    }

    public void setShowStatusDialog(boolean showStatusDialog) {
        this.showStatusDialog = showStatusDialog;
    }

    public Consulta getConsultaUpdate() {
        return consultaUpdate;
    }

    public Date getCurretlyDate(){
        Date date = new Date();

        return date;
    }

    public void setConsultaUpdate(Consulta consultaUpdate) {
        this.consultaUpdate = consultaUpdate;
    }

    public Status getStatusEscolhido() {
        return statusEscolhido;
    }

    public void setStatusEscolhido(Status statusEscolhido) {
        this.statusEscolhido = statusEscolhido;
    }

    public List<Status> getStatusChange() {
        return statusChange;
    }

    public void setStatusChange(List<Status> statusChange) {
        this.statusChange = statusChange;
    }

    public void setListaConsultas(List<Consulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        return "pendentes?faces-redirect=true";
    }

    public TimeZone getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone;
    }

    public void updateStatus() {
        Consulta consultaStatus = new Consulta();
        consultaStatus.setId(consultaUpdate.getId());
        consultaStatus.setStatus(statusEscolhido);
        ConsultaService.update(consultaStatus);
        for (int i=0; i<listaConsultas.size(); i++) {
            if (listaConsultas.get(i).getId().equals(consultaUpdate.getId())) {
                listaConsultas.get(i).setStatus(statusEscolhido);
            }
        }
        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("formPendentes");
        ajax.update("socketForm");
        RequestContext.getCurrentInstance().execute("confirmaConsulta()");



    }
}
