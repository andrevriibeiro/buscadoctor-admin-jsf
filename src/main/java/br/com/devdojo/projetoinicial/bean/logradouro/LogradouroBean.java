package br.com.devdojo.projetoinicial.bean.logradouro;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class LogradouroBean implements Serializable{

    private Consultorio consultorio;
    private final LoginBean loginBean;

    public void init() {
        consultorio = loginBean.getPerfil().getConsultorio();
    }

    @Inject
    public LogradouroBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }


}
