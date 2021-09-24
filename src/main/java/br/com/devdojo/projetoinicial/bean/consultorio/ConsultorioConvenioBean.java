package br.com.devdojo.projetoinicial.bean.consultorio;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.ConsultorioConvenio;
import br.com.devdojo.projetoinicial.service.convenioconsultorio.ConvenioConsultorioService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Andre Ribeiro on 06/06/2017.
 */
@Named
@ViewScoped
public class ConsultorioConvenioBean implements Serializable {

    private final LoginBean loginBean;
    private List<ConsultorioConvenio> consultorioConvenios;
    private List<ConsultorioConvenio> consultorioConvenioSelected = new ArrayList<>();
    private List<ConsultorioConvenio> consultorioConveniosUpdatedList = new ArrayList<>();
    private List<ConsultorioConvenio> consultorioConveniosRemoved = new ArrayList<>();

    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void init() {
        consultorioConvenios = ConvenioConsultorioService.getConveniosConsultorio(loginBean.getPerfil().getConsultorio().getId());
    }

    @Inject
    public ConsultorioConvenioBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @Transactional
    public String updateConvenio() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture.runAsync(() -> ConvenioConsultorioService.update(consultorioConveniosUpdatedList), executor)
                    .get(10, TimeUnit.SECONDS);

        CompletableFuture.runAsync(() -> ConvenioConsultorioService.delete(consultorioConveniosRemoved), executor)
                    .get(10, TimeUnit.SECONDS);

        return "convenio?faces-redirect=true";
    }

    public void onCellEdit(ConsultorioConvenio consultorioConvenio){
        consultorioConveniosUpdatedList.add(consultorioConvenio);
    }

    public void removeConvenioConsultorio() {
        consultorioConvenios.removeAll(consultorioConvenioSelected);
        consultorioConveniosRemoved.addAll(consultorioConvenioSelected);
    }

    public List<ConsultorioConvenio> getConveniosConsultorio() {return consultorioConvenios;}

    public void setConveniosConsultorio(List<ConsultorioConvenio> conveniosConsultorio) {this.consultorioConvenios = conveniosConsultorio;}

    public List<ConsultorioConvenio> getConsultorioConvenioSelected() {return consultorioConvenioSelected;}

    public void setConsultorioConvenioSelected(List<ConsultorioConvenio> consultorioConvenioSelected) {this.consultorioConvenioSelected = consultorioConvenioSelected;}
}
