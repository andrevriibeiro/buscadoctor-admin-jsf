package br.com.devdojo.projetoinicial.bean.procedimentoespecialista;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoConvenio;
import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoEspecialista;
import br.com.devdojo.projetoinicial.service.especialista.EspecialistaService;
import br.com.devdojo.projetoinicial.service.procedimentoconvenio.ProcedimentoConvenioService;
import br.com.devdojo.projetoinicial.service.procedimentoespecialista.ProcedimentoEspecialistaService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.clearCollection;

/**
 * @author André Ribeiro, William Suane on 6/7/17.
 */
@Named
@ViewScoped
public class ProcedimentoEspecialistaListBean implements Serializable {
    private int especialistaId;
    private Especialista especialista;
    private List<ProcedimentoEspecialista> procedimentoEspecialistaList;
    private List<Convenio> convenioList;
    private List<ProcedimentoConvenio> procedimentoConvenioList;
    private ProcedimentoEspecialista procedimentoEspecialista;
    private final LoginBean loginBean;
    private Set<ProcedimentoEspecialista> procedimentoEspecialistaEditadoSet = new HashSet<>();

    @Inject
    public ProcedimentoEspecialistaListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        createNewProcedimentoEspecialista();
        especialista = EspecialistaService.getEspecialistaEspecialidadeById(especialistaId);
        search();
        extractConvenioFromProcedimentoEspecialista();
    }

    private void createNewProcedimentoEspecialista() {
        procedimentoEspecialista = ProcedimentoEspecialista.newBuilder().repasse(0d).porcentagem(0d).procedimentoConvenio(ProcedimentoConvenio.newBuilder().build()).build();
    }

    private void extractConvenioFromProcedimentoEspecialista() {
        convenioList = procedimentoEspecialistaList
                .stream()
                .map(ProcedimentoEspecialista::getProcedimentoConvenio)
                .map(ProcedimentoConvenio::getConvenio)
                .collect(Collectors.toList());
        convenioList.sort(Comparator.comparing(Convenio::getNome));
    }

    public void loadProcedimentoByConvenio() {
        procedimentoConvenioList = ProcedimentoConvenioService.search(especialistaId, loginBean.getPerfil().getConsultorio().getId(), procedimentoEspecialista.getProcedimentoConvenio().getConvenio().getId());
    }

    public void search() {
        int TODOS_CONVENIOS = 0, TODOS_PROCEDIMENTOS = 0;
        int convenioId = procedimentoEspecialista.getProcedimentoConvenio().getConvenio() == null ? TODOS_CONVENIOS : procedimentoEspecialista.getProcedimentoConvenio().getConvenio().getId();
        int procedimentoId = procedimentoEspecialista.getProcedimentoConvenio().getProcedimento() == null ? TODOS_PROCEDIMENTOS : procedimentoEspecialista.getProcedimentoConvenio().getProcedimento().getId();
        procedimentoEspecialistaList = ProcedimentoEspecialistaService.searchByEspecialistaConvenioAndProcedimento(especialistaId, convenioId, loginBean.getPerfil().getConsultorio().getId(), procedimentoId);
    }

    public void onCellRepasseEdit(ProcedimentoEspecialista procedimentoEspecialista) {
        procedimentoEspecialista.setPorcentagem(0d);
        updateProcedimentoEspecialistaInsideSet(procedimentoEspecialista);
    }
    public void onCellPorcentagemEdit(ProcedimentoEspecialista procedimentoEspecialista) {
        procedimentoEspecialista.setRepasse(0d);
        updateProcedimentoEspecialistaInsideSet(procedimentoEspecialista);
    }
    public void onCellLiberadoEdit(ProcedimentoEspecialista procedimentoEspecialista) {
        updateProcedimentoEspecialistaInsideSet(procedimentoEspecialista);
    }

    private void updateProcedimentoEspecialistaInsideSet(ProcedimentoEspecialista procedimentoEspecialista) {
        procedimentoEspecialistaEditadoSet.remove(procedimentoEspecialista);
        procedimentoEspecialistaEditadoSet.add(procedimentoEspecialista);
    }

    @Transactional
    public void update() {
        ProcedimentoEspecialistaService.update(new ArrayList<>(procedimentoEspecialistaEditadoSet));
        clearCollection(procedimentoEspecialistaEditadoSet);
    }

    public ProcedimentoEspecialista getProcedimentoEspecialista() {
        return procedimentoEspecialista;
    }

    public void setProcedimentoEspecialista(ProcedimentoEspecialista procedimentoEspecialista) {
        this.procedimentoEspecialista = procedimentoEspecialista;
    }

    public List<ProcedimentoConvenio> getProcedimentoConvenioList() {
        return procedimentoConvenioList;
    }

    public void setProcedimentoConvenioList(List<ProcedimentoConvenio> procedimentoConvenioList) {
        this.procedimentoConvenioList = procedimentoConvenioList;
    }

    public Set<ProcedimentoEspecialista> getProcedimentoEspecialistaEditadoSet() {
        return procedimentoEspecialistaEditadoSet;
    }

    public void setProcedimentoEspecialistaEditadoSet(Set<ProcedimentoEspecialista> procedimentoEspecialistaEditadoSet) {
        this.procedimentoEspecialistaEditadoSet = procedimentoEspecialistaEditadoSet;
    }

    public List<ProcedimentoEspecialista> getProcedimentoEspecialistaList() {
        return procedimentoEspecialistaList;
    }

    public void setProcedimentoEspecialistaList(List<ProcedimentoEspecialista> procedimentoEspecialistaList) {
        this.procedimentoEspecialistaList = procedimentoEspecialistaList;
    }

    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
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

}
