package br.com.devdojo.projetoinicial.bean.especialista;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Especialidade;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaEspecialidade;
import br.com.devdojo.projetoinicial.persistence.model.Logradouro;
import br.com.devdojo.projetoinicial.service.especialidade.EspecialidadeService;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;
import br.com.devdojo.projetoinicial.service.especialistaespecialidade.EspecialistaEspecialidadeService;
import br.com.devdojo.projetoinicial.service.logradouro.LogradouroService;
import org.apache.commons.lang3.text.WordUtils;

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
public class EspecialistaBean implements Serializable {

    private final LoginBean loginBean;
    private EspecialistaConsultorio especialistaConsultorio;
    private int especialistaId;
    private List<EspecialistaEspecialidade> especialistaEspecialidadeList;
    private List<EspecialistaEspecialidade> especialidadeSelectedList = new ArrayList<>();
    private List<EspecialistaEspecialidade> especialidadesRemovedList = new ArrayList<>();
    private List<Especialidade> especialidades;
    private Especialidade newEspecialidade;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        newEspecialidade = new Especialidade();

        especialidades = CompletableFuture.supplyAsync(EspecialidadeService::getAllEspecialidades, executor)
                .get(10, TimeUnit.SECONDS);

        especialistaConsultorio = EspecialistaConsultorioService
                .search(especialistaId, loginBean.getPerfil().getConsultorio().getId());

        if (especialistaConsultorio.getEspecialista().getLogradouro() == null) {
            especialistaConsultorio.getEspecialista().setLogradouro(Logradouro.newBuilder().build());
        }

        especialistaEspecialidadeList = CompletableFuture.supplyAsync(() -> EspecialistaEspecialidadeService
                .getEspecialistaEspecialidadeByEspecialista(especialistaId), executor).get(10, TimeUnit.SECONDS);

        especialidades.removeAll(especialistaEspecialidadeList.stream()
                .map(EspecialistaEspecialidade::getEspecialidade)
                .collect(Collectors.toList()));
    }

    @Inject
    public EspecialistaBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void findCep() {
        Logradouro logEncontrado = LogradouroService.searchLogradouroOnDatabase(especialistaConsultorio
                .getEspecialista().getLogradouro().getCep());
        especialistaConsultorio.getEspecialista().setLogradouro(logEncontrado);
    }

    public void addEspecialidadeAndRemoveFromDropdown() {
        especialistaEspecialidadeList.add(EspecialistaEspecialidade.newBuilder()
                .especialidade(newEspecialidade)
                .especialista(especialistaConsultorio.getEspecialista())
                .principal(!(especialistaEspecialidadeList.size() > 0))
                .id(0)
                .build());

        especialidades.removeAll(especialistaEspecialidadeList.stream()
                .map(EspecialistaEspecialidade::getEspecialidade)
                .collect(Collectors.toList()));
    }

    public void removeEspecialidadeAndAddToDropdown() {
        especialistaEspecialidadeList.removeAll(especialidadeSelectedList);
        especialidadesRemovedList.addAll(especialidadeSelectedList);

        especialidades.addAll(especialistaEspecialidadeList.stream()
                .map(EspecialistaEspecialidade::getEspecialidade)
                .collect(Collectors.toList()));
    }

    @Transactional
    public String salvar() throws InterruptedException, ExecutionException, TimeoutException {
        especialistaConsultorio.getEspecialista().setConsultorio(loginBean.getPerfil().getConsultorio());
        especialistaConsultorio.getEspecialista().setEspecialidade(especialidades);
        especialistaConsultorio.getEspecialista().setNome(WordUtils.capitalizeFully(especialistaConsultorio.getEspecialista().getNome()));
        especialistaConsultorio.getEspecialista().setRazaoSocial(WordUtils.capitalizeFully(especialistaConsultorio.getEspecialista().getRazaoSocial()));
        especialistaEspecialidadeList.get(0).setEspecialista(especialistaConsultorio.getEspecialista());

        especialidadesRemovedList.forEach(EspecialistaEspecialidadeService::deleteEspecialistaEspecialidade);

        CompletableFuture.runAsync(() -> EspecialistaEspecialidadeService.updateEspecialistaEspecialidade(especialistaEspecialidadeList), executor)
                .get(10, TimeUnit.SECONDS);

        CompletableFuture.runAsync(() -> EspecialistaConsultorioService.update(especialistaConsultorio), executor)
                .get(10, TimeUnit.SECONDS);

        loginBean.loadConsultorioEspecialistaEspecialidade();

        return "editar?especialistaId=" + especialistaId + "&faces-redirect=true";
    }

    public void changeEspecialidadeprincipal(EspecialistaEspecialidade especialistaEspecialidadeSelecionado) {
        especialistaEspecialidadeList.stream()
                .filter(EspecialistaEspecialidade::isPrincipal)
                .filter(especialistaEspecialidade -> !especialistaEspecialidade.getEspecialidade().getEspecialidade()
                        .equals(especialistaEspecialidadeSelecionado.getEspecialidade().getEspecialidade()))
                .findFirst()
                .ifPresent(especialistaEspecialidade -> especialistaEspecialidade.setPrincipal(false));
    }

    public EspecialistaConsultorio getEspecialistaConsultorio() {
        return especialistaConsultorio;
    }

    public void setEspecialistaConsultorio(EspecialistaConsultorio especialistaConsultorio) {
        this.especialistaConsultorio = especialistaConsultorio;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public List<EspecialistaEspecialidade> getEspecialistaEspecialidadeList() {
        return especialistaEspecialidadeList;
    }

    public void setEspecialistaEspecialidadeList(List<EspecialistaEspecialidade> especialistaEspecialidadeList) {
        this.especialistaEspecialidadeList = especialistaEspecialidadeList;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public Especialidade getNewEspecialidade() {
        return newEspecialidade;
    }

    public void setNewEspecialidade(Especialidade newEspecialidade) {
        this.newEspecialidade = newEspecialidade;
    }

    public List<EspecialistaEspecialidade> getEspecialidadeSelectedList() {
        return especialidadeSelectedList;
    }

    public void setEspecialidadeSelectedList(List<EspecialistaEspecialidade> especialidadeSelectedList) {
        this.especialidadeSelectedList = especialidadeSelectedList;
    }
}