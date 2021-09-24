package br.com.devdojo.projetoinicial.bean.paciente;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Logradouro;
import br.com.devdojo.projetoinicial.persistence.model.Paciente;
import br.com.devdojo.projetoinicial.persistence.model.UsuarioConvenio;
import br.com.devdojo.projetoinicial.persistence.model.dto.PacienteDTO;
import br.com.devdojo.projetoinicial.persistence.model.enums.Sexo;
import br.com.devdojo.projetoinicial.service.convenio.ConvenioService;
import br.com.devdojo.projetoinicial.service.logradouro.LogradouroService;
import br.com.devdojo.projetoinicial.service.paciente.PacienteService;
import br.com.devdojo.projetoinicial.service.usuario.UsuarioConvenioService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Created by Andre Ribeiro on 14/04/17.
 */
@Named
@ViewScoped
public class PacienteEditBean implements Serializable {

    private int pacienteId;
    private int usuarioId;
    private Paciente paciente;
    private List<UsuarioConvenio> usuarioConvenioList;
    private List<UsuarioConvenio> usuarioConveniosRemovidoList = new ArrayList<>();
    private List<UsuarioConvenio> usuarioConvenioListSelected = new ArrayList<>();
    private Sexo sexo;
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final LoginBean loginBean;
    private List<Convenio> convenioList;
    private UsuarioConvenio newUsuarioConvenio;

    @Inject
    public PacienteEditBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init(){
        paciente = getPacienteById(pacienteId);
        usuarioConvenioList = getUsuarioConvenios(usuarioId);
        convenioList = loadConvenios();
        newUsuarioConvenio = new UsuarioConvenio().newBuilder().build();
    }

    public void findCep() {
        Logradouro logEncontrado = LogradouroService.searchLogradouroOnDatabase(paciente.getUsuario().getLogradouro().getCep());

        paciente.getUsuario().setLogradouro(logEncontrado);
    }

    public void removeUsuarioConvenio() {
        usuarioConvenioList.removeAll(usuarioConvenioListSelected);

        usuarioConvenioListSelected.forEach(usuarioConvenio -> {
            if(usuarioConvenio.getId()>0)
                usuarioConveniosRemovidoList.addAll(usuarioConvenioListSelected);
        });

    }

    @Transactional
    public void addUsuarioConvenio(){
        if(newUsuarioConvenio.getConvenio()!=null){
            newUsuarioConvenio.setId(0);
            usuarioConvenioList.add(newUsuarioConvenio);
        }else{
            FacesUtils.addWarningMessage(newUsuarioConvenio.getConvenio() == null ?
                    "warningNenhumConvenioSelecionado" : "warningNenhumConvenioSelecionado", false);
        }

        newUsuarioConvenio = null;
        newUsuarioConvenio = new UsuarioConvenio().newBuilder().build();
    }

    public List<Convenio> loadConvenios(){
        return ConvenioService.getAllConvenios();
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException{

        if(paciente.getUsuario().getCelular() != null){
            String celular = Functions.getMeMyNumber(paciente.getUsuario().getCelular());
            paciente.getUsuario().setCelular(celular);
        }
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setPaciente(paciente);
        pacienteDTO.setUsuarioConvenios(usuarioConvenioList);
        PacienteService.update(pacienteDTO);

        usuarioConveniosRemovidoList.forEach(UsuarioConvenioService::removeUsuarioConvenio);

        return "editar?pacienteId="+pacienteId+"&usuarioId="+usuarioId+"&faces-redirect=true";
    }

    public Paciente getPaciente() {return paciente;}

    public int getPacienteId(){
        return pacienteId;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setPacienteId(int pacienteId){
        this.pacienteId = pacienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<UsuarioConvenio> getUsuarioConvenioList() {return usuarioConvenioList;}

    public void setUsuarioConvenioList(List<UsuarioConvenio> usuarioConvenioList) {this.usuarioConvenioList = usuarioConvenioList;}

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Paciente getPacienteById(int id) {
        return PacienteService.getPacienteById(id);
    }

    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    public List<UsuarioConvenio> getUsuarioConvenios(int usuarioId){return UsuarioConvenioService.getUsuarioConvenios(usuarioId);}

    public List<UsuarioConvenio> getUsuarioConvenioListSelected() {
        return usuarioConvenioListSelected;
    }

    public void setUsuarioConvenioListSelected(List<UsuarioConvenio> usuarioConvenioListSelected) {this.usuarioConvenioListSelected = usuarioConvenioListSelected;}

    public UsuarioConvenio getNewUsuarioConvenio() {
        return newUsuarioConvenio;
    }

    public void setNewUsuarioConvenio(UsuarioConvenio newUsuarioConvenio) {this.newUsuarioConvenio = newUsuarioConvenio;}

}
