package br.com.devdojo.projetoinicial.bean.especialista;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Especialidade;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaEspecialidade;
import br.com.devdojo.projetoinicial.service.especialidade.EspecialidadeService;
import br.com.devdojo.projetoinicial.service.especialista.EspecialistaService;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;
import br.com.devdojo.projetoinicial.service.especialistaespecialidade.EspecialistaEspecialidadeService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class EspecialistaRegisterBean implements Serializable {
    private Especialista especialista = Especialista.newBuilder().id(0).build();
    private final LoginBean loginBean;
    private List<Especialidade> especialidades;
    private Especialidade newEspecialidade;
    private List<Especialidade> especialidadesSelecionadas = new ArrayList<>();
    private List<EspecialistaEspecialidade> especialistaEspecialidadeList = new ArrayList<>();
    private List<EspecialistaEspecialidade> especialidadeSelectedList = new ArrayList<>();
    private List<EspecialistaEspecialidade> especialidadesRemovedList = new ArrayList<>();
    private boolean disabledFields = true;
    private boolean disabledSaveButton = true;
    private boolean trabalhaferiado;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Inject
    public EspecialistaRegisterBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        especialidades = CompletableFuture.supplyAsync(EspecialidadeService::getAllEspecialidades, executor).get(10, TimeUnit.SECONDS);
    }

    public void findByCrm() {
        disabledSaveButton = isEspecialistaAlreadyAssociatedWithConsultorio();
        disabledFields = disabledSaveButton || isEspecialistaAlreadyRegistredOnDatabase();
    }

    private boolean isEspecialistaAlreadyAssociatedWithConsultorio() {
        boolean especialistaAlreadyRegistered = loginBean.getEspecialistaEspecialidadeMap()
                .keySet()
                .stream()
                .anyMatch(espec -> espec.getCrm().trim().equals(especialista.getCrm().trim()));
        if (especialistaAlreadyRegistered)
            FacesUtils.addErrorMessageNoBundle("Especialista já está Cadastrado", false);
        return especialistaAlreadyRegistered;
    }

    private boolean isEspecialistaAlreadyRegistredOnDatabase() {
        List<Especialista> especialistaEncontradoByCRM = EspecialistaService.findByCrm(especialista.getCrm());
        if (especialistaEncontradoByCRM.size() == 0)
            return false;
        especialista = especialistaEncontradoByCRM.get(0);
        especialistaEspecialidadeList = EspecialistaEspecialidadeService
                .getEspecialistaEspecialidadeByEspecialista(especialista.getId());
        return true;
    }

    public void changeEspecialidadeprincipal(EspecialistaEspecialidade especialistaEspecialidadeSelecionado) {
        especialistaEspecialidadeList.stream()
                .filter(EspecialistaEspecialidade::isPrincipal)
                .filter(especialistaEspecialidade -> !especialistaEspecialidade.getEspecialidade().getEspecialidade()
                        .equals(especialistaEspecialidadeSelecionado.getEspecialidade().getEspecialidade()))
                .findFirst()
                .ifPresent(especialistaEspecialidade -> especialistaEspecialidade.setPrincipal(false));
    }

    public void addEspecialidadeAndRemoveFromDropdown() {
        if (newEspecialidade == null) {
            FacesUtils.addWarningMessage(newEspecialidade == null ?
                    "warnngNenhumaEspecialidadeSelecionada" : "warnngNenhumaEspecialidadeSelecionada", false);
        }else{
            especialistaEspecialidadeList.add(EspecialistaEspecialidade.newBuilder()
                    .especialidade(newEspecialidade)
                    .especialista(especialista)
                    .principal(!(especialistaEspecialidadeList.size() > 0))
                    .id(0)
                    .build());

            especialidades.removeAll(especialistaEspecialidadeList.stream()
                    .map(EspecialistaEspecialidade::getEspecialidade)
                    .collect(Collectors.toList()));
        }
    }

    public void removeEspecialidadeAndAddToDropdown() {
        especialistaEspecialidadeList.removeAll(especialidadeSelectedList);
        especialidadesRemovedList.addAll(especialidadeSelectedList);

        especialidades.addAll(especialistaEspecialidadeList.stream()
                .map(EspecialistaEspecialidade::getEspecialidade)
                .collect(Collectors.toList()));
    }

    public String cadastrar() {
        return especialista.getId() != null && especialista.getId() > 0 ? associateEspecialistaWithConsultorio() : createEspecialistaOnDatabase();
    }

    private String createEspecialistaOnDatabase() {
        if(especialistaEspecialidadeList.size()>=1) {
            especialista.setNome(WordUtils.capitalizeFully(especialista.getNome()));
            especialista.setConsultorio(loginBean.getPerfil().getConsultorio());
            especialistaEspecialidadeList.get(0).setEspecialista(especialista);
            List<EspecialistaEspecialidade> especialistaEspecialidades = EspecialistaEspecialidadeService.create(especialistaEspecialidadeList);
            especialista = especialistaEspecialidades.get(0).getEspecialista();
            associateEspecialistaWithConsultorio();

            return "editar?especialistaId=" + especialista.getId() + "&faces-redirect=true";

        }else{
            FacesUtils.addWarningMessage(newEspecialidade == null ?
                    "warningEspecialistaSemEspecialidade" : "warningEspecialistaSemEspecialidade", false);
            return null;
        }
    }

    private String associateEspecialistaWithConsultorio() {
        EspecialistaConsultorioService.associateEspecialista(EspecialistaConsultorio.newBuilder()
                .consultorio(loginBean.getPerfil().getConsultorio())
                .especialista(especialista)
                .trabalhaferiado(trabalhaferiado)
                .habilitado(true).build());
        loginBean.addEspecialistaEspecialidade(especialista);

        return "editar?especialistaId=" + especialista.getId() + "&faces-redirect=true";
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public boolean isDisabledFields() {
        return disabledFields;
    }

    public void setDisabledFields(boolean disabledFields) {
        this.disabledFields = disabledFields;
    }

    public List<Especialidade> getEspecialidadesSelecionadas() {
        return especialidadesSelecionadas;
    }

    public Especialidade getNewEspecialidade() {
        return newEspecialidade;
    }

    public void setNewEspecialidade(Especialidade newEspecialidade) {
        this.newEspecialidade = newEspecialidade;
    }

    public void setEspecialidadesSelecionadas(List<Especialidade> especialidadesSelecionadas) {this.especialidadesSelecionadas = especialidadesSelecionadas;}

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public List<EspecialistaEspecialidade> getEspecialistaEspecialidadeList() {
        return especialistaEspecialidadeList;
    }

    public void setEspecialistaEspecialidadeList(List<EspecialistaEspecialidade> especialistaEspecialidadeList) {this.especialistaEspecialidadeList = especialistaEspecialidadeList;}

    public boolean isDisabledSaveButton() {
        return disabledSaveButton;
    }

    public void setDisabledSaveButton(boolean disabledSaveButton) {
        this.disabledSaveButton = disabledSaveButton;
    }

    public boolean isTrabalhaferiado() {
        return trabalhaferiado;
    }

    public void setTrabalhaferiado(boolean trabalhaferiado) {
        this.trabalhaferiado = trabalhaferiado;
    }
}
