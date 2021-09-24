package br.com.devdojo.projetoinicial.bean.paciente;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.*;
import br.com.devdojo.projetoinicial.persistence.model.dto.PacienteDTO;
import br.com.devdojo.projetoinicial.service.paciente.PacienteService;
import br.com.devdojo.projetoinicial.service.usuario.UsuarioConvenioService;
import br.com.devdojo.projetoinicial.service.usuario.UsuarioService;
import br.com.devdojo.projetoinicial.utils.Functions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static br.com.devdojo.projetoinicial.utils.Functions.clearCollection;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class PacienteRegisterBean implements Serializable {
    private Paciente paciente;
    private boolean novoUsuario;
    private boolean enableFields;
    private List<Usuario> usuarioList;
    private List<UsuarioConvenio> usuarioConvenioList;
    private Convenio convenio;
    private UsuarioConvenio newUsuarioConvenio = new UsuarioConvenio();
    private final LoginBean loginBean;
    private Logradouro logradouro;

    @Inject
    public PacienteRegisterBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @PostConstruct
    public void init() {
        paciente = newPaciente();
        enableFields = true;
        novoUsuario = true;

        logradouro = Logradouro.newBuilder().id(1).build();
    }

    public void rowSelected(SelectEvent event) {
        novoUsuario = true;
        enableFields = false;

        this.paciente.setUsuario((Usuario) event.getObject());

        usuarioConvenioList = UsuarioConvenioService.getUsuarioConvenios(paciente.getUsuario().getId());

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
        ajax.update("form2");
    }

    public void newUserPressed(){
        novoUsuario = true;
        enableFields = false;

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
    }

    public void searchUsuarios() {
        novoUsuario = false;

        usuarioList = UsuarioService.searchUsuarios(paciente.getUsuario());

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form2");
    }

    private Paciente newPaciente() {
        return Paciente
                .newBuilder()
                .id(0)
                .usuario(Usuario.newBuilder()
                        .id(0)
                        .logradouro(logradouro)
                        .build())
                .consultorio(loginBean.getPerfil().getConsultorio())
                .build();
    }

    public void clearSearchFields() {
        novoUsuario = true;
        enableFields = true;
        paciente = newPaciente();
        clearCollection(usuarioList);
        clearCollection(usuarioConvenioList);

        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
        ajax.update("form2");
        ajax.update("form3");
    }

    @Transactional
    public String createUsuarioPaciente(){

        if(paciente.getUsuario().getCelular() != null){
            String celular = Functions.getMeMyNumber(paciente.getUsuario().getCelular());
            paciente.getUsuario().setCelular(celular);
        }
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setPaciente(paciente);
        Paciente paciente = PacienteService.create(pacienteDTO);

        return "editar?pacienteId="+paciente.getId()+"&usuarioId="+paciente.getUsuario().getId()+"&faces-redirect=true";
    }

    public boolean isNovoUsuario() {return novoUsuario;}

    public void setNovoUsuario(boolean novoUsuario) {
        this.novoUsuario = novoUsuario;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Logradouro getLogradouro() {return logradouro;}

    public void setLogradouro(Logradouro logradouro) {this.logradouro = logradouro;}

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Convenio getConvenio() {return convenio;}

    public void setConvenio(Convenio convenio) {this.convenio = convenio;}

    public boolean isEnableFields() {return enableFields;}

    public List<UsuarioConvenio> getUsuarioConvenioList() {return usuarioConvenioList;}

    public void setUsuarioConvenioList(List<UsuarioConvenio> usuarioConvenioList) {this.usuarioConvenioList = usuarioConvenioList;}

}
