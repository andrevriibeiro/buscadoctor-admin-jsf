package br.com.devdojo.projetoinicial.bean.consultorio;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.Logradouro;
import br.com.devdojo.projetoinicial.service.consultorio.ConsultorioService;
import br.com.devdojo.projetoinicial.service.logradouro.LogradouroService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ConsultorioBean implements Serializable{

    private Consultorio consultorio;
    private final LoginBean loginBean;

    public void init() {
        consultorio = loginBean.getPerfil().getConsultorio();
    }

    @Inject
    public ConsultorioBean(LoginBean loginBean) {this.loginBean = loginBean;}

    public void findCep() {
        Logradouro logEncontrado = LogradouroService.searchLogradouroOnDatabase(consultorio.getLogradouro().getCep());
        consultorio.setLogradouro(logEncontrado);
    }

    public String salvar(){
        consultorio.setLimiteEspecialistas(loginBean.getPerfil().getConsultorio().getLimiteEspecialistas());
        ConsultorioService.update(consultorio);

        return "consultorio?faces-redirect=true";
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }
}
