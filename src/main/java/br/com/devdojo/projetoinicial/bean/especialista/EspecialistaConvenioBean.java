package br.com.devdojo.projetoinicial.bean.especialista;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.ConsultorioConvenio;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.dto.EspecialistaConvenioDTO;
import br.com.devdojo.projetoinicial.service.convenioconsultorio.ConvenioConsultorioService;
import br.com.devdojo.projetoinicial.service.especialista.EspecialistaService;
import br.com.devdojo.projetoinicial.service.especialistaconvenio.EspecialistaConvenioService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by André Ribeiro, William Suane on 3/30/17.
 */
@Named
@ViewScoped
public class EspecialistaConvenioBean implements Serializable {

    private final LoginBean loginBean;
    private int especialistaId;
    private Especialista especialista;
    private List<Convenio> convenios = new ArrayList<>();
    private List<ConsultorioConvenio> consultorioConvenios;
    private List<Convenio> convenioRemovidosList = new ArrayList<>();
    private DualListModel<Convenio> dualConvenios;
    private EspecialistaConvenioDTO especialistaConvenio;
    private EspecialistaConvenioDTO especialistaConvenioRemover;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void init(){
        especialistaConvenio = new EspecialistaConvenioDTO();
        especialistaConvenioRemover = new EspecialistaConvenioDTO();
        especialista = EspecialistaService.getEspecialistaEspecialidadeById(especialistaId);
        consultorioConvenios = ConvenioConsultorioService.getConveniosConsultorio(loginBean.getPerfil().getConsultorio().getId());
        convenios.addAll(consultorioConvenios.stream().map(ConsultorioConvenio::getConvenio).collect(Collectors.toList()));
        //convenios = ConvenioService.getAllConvenios();
        especialistaConvenio.setConvenios(EspecialistaConvenioService.search(especialista.getId(), loginBean.getPerfil().getConsultorio().getId()));
        convenios.removeAll(especialistaConvenio.getConvenios());
        dualConvenios = new DualListModel<>(convenios, especialistaConvenio.getConvenios());
    }

    @Inject
    public EspecialistaConvenioBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void setDualConvenios(DualListModel<Convenio> dualConvenios) {
        this.dualConvenios = dualConvenios;
    }

    public void onTransfer(TransferEvent event) {
        if (event.isAdd()) {
            for(Object item : event.getItems()) {
                especialistaConvenio.getConvenios().add(((Convenio) item ));
            }
        }else {
            for(Object item : event.getItems()) {
                especialistaConvenio.getConvenios().remove((item));
                convenioRemovidosList.add(((Convenio) item ));
            }
        }
    }

    public void onSelect(SelectEvent event) {
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder() {
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    public void updateConvenioEspecialista() throws InterruptedException, ExecutionException, TimeoutException {
        especialistaConvenio.setConsultorio(loginBean.getPerfil().getConsultorio().getId());
        especialistaConvenio.setEspecialista(especialista);
        especialistaConvenioRemover.setConsultorio(loginBean.getPerfil().getConsultorio().getId());
        especialistaConvenioRemover.setEspecialista(especialista);
        especialistaConvenioRemover.setConvenios(convenioRemovidosList);

        CompletableFuture.runAsync(() -> EspecialistaConvenioService.updateConvenioEspecialista(especialistaConvenio),executor)
                .get(10, TimeUnit.SECONDS);
        CompletableFuture.runAsync(() -> EspecialistaConvenioService.deleteConvenioEspecialista(especialistaConvenioRemover), executor)
                .get(10, TimeUnit.SECONDS);

        FacesUtils.addSuccessMessage("successUpdate", true);

    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public List<Convenio> getConvenios() {
        return convenios;
    }

    public void setConvenios(List<Convenio> convenios) {
        this.convenios = convenios;
    }

    public DualListModel<Convenio> getDualConvenios() {return dualConvenios;}

    public Especialista getEspecialista() {return especialista;}

    public void setEspecialista(Especialista especialista) {this.especialista = especialista;}
}
