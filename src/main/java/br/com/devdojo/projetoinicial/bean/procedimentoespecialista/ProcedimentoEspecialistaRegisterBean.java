package br.com.devdojo.projetoinicial.bean.procedimentoespecialista;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoConvenio;
import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoEspecialista;
import br.com.devdojo.projetoinicial.service.especialista.EspecialistaService;
import br.com.devdojo.projetoinicial.service.especialistaconvenio.EspecialistaConvenioService;
import br.com.devdojo.projetoinicial.service.procedimentoconvenio.ProcedimentoConvenioService;
import br.com.devdojo.projetoinicial.service.procedimentoespecialista.ProcedimentoEspecialistaService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.clearCollection;

/**
 * @author André Ribeiro, William Suane on 6/7/17.
 */
@Named
@ViewScoped
public class ProcedimentoEspecialistaRegisterBean implements Serializable {
    private int especialistaId;
    private Especialista especialista;
    private List<Convenio> convenioList;
    private List<ProcedimentoConvenio> procedimentoConvenioList;
    private List<ProcedimentoEspecialista> procedimentoEspecialistaList;
    private ProcedimentoEspecialista procedimentoEspecialista;
    private final LoginBean loginBean;

    @Inject
    public ProcedimentoEspecialistaRegisterBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        especialista = EspecialistaService.getEspecialistaEspecialidadeById(especialistaId);
        convenioList = EspecialistaConvenioService.search(especialistaId, loginBean.getPerfil().getConsultorio().getId());
        procedimentoEspecialistaList = ProcedimentoEspecialistaService.searchByEspecialistaConvenioAndProcedimento(especialistaId, 0, loginBean.getPerfil().getConsultorio().getId(), 0);
        createNewProcedimentoEspecialista();
    }

    private void createNewProcedimentoEspecialista() {
        procedimentoEspecialista = ProcedimentoEspecialista.newBuilder()
                .repasse(0d)
                .porcentagem(0d)
                .liberado(true)
                .procedimentoConvenio(ProcedimentoConvenio.newBuilder().build())
                .especialista(especialista)
                .build();
    }

    public void loadProcedimentoByConvenio() {
        procedimentoConvenioList = ProcedimentoConvenioService.search(especialistaId, loginBean.getPerfil().getConsultorio().getId(), procedimentoEspecialista.getProcedimentoConvenio().getConvenio().getId());
        procedimentoConvenioList.removeAll(procedimentoEspecialistaList.stream()
                .map(ProcedimentoEspecialista::getProcedimentoConvenio)
                .collect(Collectors.toList()));
    }

    public void removePorcentagem() {
        if (procedimentoEspecialista.getRepasse() != 0)
            procedimentoEspecialista.setPorcentagem(0d);
    }

    public void removeRepasse() {
        if (procedimentoEspecialista.getPorcentagem() != 0)
            procedimentoEspecialista.setRepasse(0d);
    }

    @Transactional
    public String create() {
        ProcedimentoEspecialistaService.create(procedimentoEspecialista);
        createNewProcedimentoEspecialista();
        clearCollection(procedimentoConvenioList);

        return "criarprocedimentoespecialista?especialistaId="+especialistaId+"faces-redirect=true";
    }

    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    public List<ProcedimentoConvenio> getProcedimentoConvenioList() {
        return procedimentoConvenioList;
    }

    public void setProcedimentoConvenioList(List<ProcedimentoConvenio> procedimentoConvenioList) {this.procedimentoConvenioList = procedimentoConvenioList;}

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public ProcedimentoEspecialista getProcedimentoEspecialista() {
        return procedimentoEspecialista;
    }

    public void setProcedimentoEspecialista(ProcedimentoEspecialista procedimentoEspecialista) {this.procedimentoEspecialista = procedimentoEspecialista;}
}
