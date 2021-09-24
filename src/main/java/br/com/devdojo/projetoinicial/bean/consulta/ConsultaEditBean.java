package br.com.devdojo.projetoinicial.bean.consulta;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.FaturaProcedimento;
import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoEspecialista;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.service.faturaprocedimento.FaturaProcedimentoService;
import br.com.devdojo.projetoinicial.service.procedimentoespecialista.ProcedimentoEspecialistaService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class ConsultaEditBean implements Serializable {
    private final LoginBean loginBean;
    private Consulta consulta;
    private String consultaId;
    private int pacienteId;
    private String consultasUrl;
    private FaturaProcedimento newFaturaProcedimento;
    private List<ProcedimentoEspecialista> procedimentoEspecialistaList;
    private List<FaturaProcedimento> faturaProcedimentoList;
    private List<FaturaProcedimento> faturaProcedimentoSelectedList = new ArrayList<>();
    private FaturaProcedimento faturaProcedimento;
    private List<FaturaProcedimento> faturaProcedimentoRemovidosList = new ArrayList<>();
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Inject
    public ConsultaEditBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        consulta = ConsultaService.getConsulta(consultaId);
        consultasUrl = "consultas?i=1&usuarioId=" + consulta.getPaciente().getUsuario().getId() + "&pacienteNome=" + consulta.getPaciente().getUsuario().getNome() + "&pacienteId=" + consulta.getPaciente().getId() + "&faces-redirect=true";
        faturaProcedimentoList = FaturaProcedimentoService.getFaturaProcedimentoByConsulta(consulta.getId());
        faturaProcedimento = !faturaProcedimentoList.isEmpty() ? faturaProcedimentoList.get(0) : new FaturaProcedimento();
        procedimentoEspecialistaList = ProcedimentoEspecialistaService.search(faturaProcedimento.getProcedimentoEspecialista().getEspecialista().getId(),
                faturaProcedimento.getProcedimentoEspecialista().getProcedimentoConvenio().getConvenio().getId(), loginBean.getPerfil().getConsultorio().getId(), true);
        procedimentoEspecialistaList.removeAll(faturaProcedimentoList.stream().map(FaturaProcedimento::getProcedimentoEspecialista).collect(Collectors.toList()));
        createNewFaturaProcedimento();
    }

    @Transactional
    public void addFaturaProcedimento() {
        faturaProcedimentoList.add(FaturaProcedimentoService.save(newFaturaProcedimento));
        procedimentoEspecialistaList.remove(newFaturaProcedimento.getProcedimentoEspecialista());
        createNewFaturaProcedimento();
    }

    private void createNewFaturaProcedimento() {
        this.newFaturaProcedimento = FaturaProcedimento.newBuilder().quantidade(1).fatura(faturaProcedimento.getFatura()).consulta(faturaProcedimento.getConsulta()).build();
    }

    public void removeProcedimentoFromFatura() {
        //    Fatura não pode ficar sem procedimento
        if (faturaProcedimentoList.size() == faturaProcedimentoSelectedList.size() || faturaProcedimentoSelectedList.size() == 0) {
            FacesUtils.addErrorMessage(faturaProcedimentoSelectedList.size() == 0 ? "errorSelecionarUmProcedimento" : "errorUmProcedimentoNecessario", false);
            throw new IllegalArgumentException();
        }
        faturaProcedimentoSelectedList.forEach(fp -> {
            faturaProcedimentoList.remove(fp);
            faturaProcedimentoRemovidosList.add(fp);
        });
    }

    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture.supplyAsync(() -> FaturaProcedimentoService.delete(faturaProcedimentoRemovidosList), executor).get(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> FaturaProcedimentoService.update(faturaProcedimentoList), executor).get(10, TimeUnit.SECONDS);

        return consultasUrl;
    }

    public String getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(String consultaId) {
        this.consultaId = consultaId;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getConsultasUrl() {
        return consultasUrl;
    }

    public void setConsultasUrl(String consultasUrl) {
        this.consultasUrl = consultasUrl;
    }

    public FaturaProcedimento getNewFaturaProcedimento() {
        return newFaturaProcedimento;
    }

    public void setNewFaturaProcedimento(FaturaProcedimento newFaturaProcedimento) {
        this.newFaturaProcedimento = newFaturaProcedimento;
    }

    public List<ProcedimentoEspecialista> getProcedimentoEspecialistaList() {
        return procedimentoEspecialistaList;
    }

    public void setProcedimentoEspecialistaList(List<ProcedimentoEspecialista> procedimentoEspecialistaList) {
        this.procedimentoEspecialistaList = procedimentoEspecialistaList;
    }

    public List<FaturaProcedimento> getFaturaProcedimentoList() {
        return faturaProcedimentoList;
    }

    public void setFaturaProcedimentoList(List<FaturaProcedimento> faturaProcedimentoList) {
        this.faturaProcedimentoList = faturaProcedimentoList;
    }

    public List<FaturaProcedimento> getFaturaProcedimentoSelectedList() {
        return faturaProcedimentoSelectedList;
    }

    public void setFaturaProcedimentoSelectedList(List<FaturaProcedimento> faturaProcedimentoSelectedList) {
        this.faturaProcedimentoSelectedList = faturaProcedimentoSelectedList;
    }

    public FaturaProcedimento getFaturaProcedimento() {
        return faturaProcedimento;
    }

    public void setFaturaProcedimento(FaturaProcedimento faturaProcedimento) {
        this.faturaProcedimento = faturaProcedimento;
    }

    public List<FaturaProcedimento> getFaturaProcedimentoRemovidosList() {
        return faturaProcedimentoRemovidosList;
    }

    public void setFaturaProcedimentoRemovidosList(List<FaturaProcedimento> faturaProcedimentoRemovidosList) {
        this.faturaProcedimentoRemovidosList = faturaProcedimentoRemovidosList;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }
}
