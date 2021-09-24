package br.com.devdojo.projetoinicial.bean.paciente;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Paciente;
import br.com.devdojo.projetoinicial.persistence.model.UsuarioConvenio;
import br.com.devdojo.projetoinicial.service.paciente.PacienteService;
import br.com.devdojo.projetoinicial.service.usuario.UsuarioConvenioService;
import org.primefaces.context.RequestContext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Andre Ribeiro on 14/04/17.
 */
@Named
@ViewScoped
public class PacienteProntuarioBean implements Serializable {

    private final LoginBean loginBean;
    private int pacienteId;
    private Paciente paciente;
    private List<UsuarioConvenio> usuarioConvenioList;
    private Date data;
    private String endereco;
    private UsuarioConvenio newUsuarioConvenio;
    private String carteirinha = "";
    private Boolean print;

    @Inject
    public PacienteProntuarioBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init(){
        data = new Date();
        print = false;
        paciente = PacienteService.getPacienteById(pacienteId);
        usuarioConvenioList = UsuarioConvenioService.getUsuarioConvenios(paciente.getUsuario().getId());
        if (usuarioConvenioList.size() > 0) {
            newUsuarioConvenio = usuarioConvenioList.get(0);
        } else {
            newUsuarioConvenio = new UsuarioConvenio();
        }


    }

    public void updateForm() {
        print = true;
        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("form");
        RequestContext.getCurrentInstance().execute("PF('confirmDlg').show();");

    }

    public String refreshPage() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        return "prontuarios?pacienteId="+pacienteId+"&i=2&faces-redirect=true";

    }

    public int getPacienteId(){
        return pacienteId;
    }

    public void setPacienteId(int pacienteId){
        this.pacienteId = pacienteId;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<UsuarioConvenio> getUsuarioConvenioList() {
        return usuarioConvenioList;
    }

    public void setUsuarioConvenioList(List<UsuarioConvenio> usuarioConvenioList) {
        this.usuarioConvenioList = usuarioConvenioList;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public UsuarioConvenio getNewUsuarioConvenio() {
        return newUsuarioConvenio;
    }

    public void setNewUsuarioConvenio(UsuarioConvenio newUsuarioConvenio) {
        this.newUsuarioConvenio = newUsuarioConvenio;
    }

    public String getCarteirinha() {
        return carteirinha;
    }

    public void setCarteirinha(String carteirinha) {
        this.carteirinha = carteirinha;
    }



}
