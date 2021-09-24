package br.com.devdojo.projetoinicial.bean.perfil;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Perfil;
import br.com.devdojo.projetoinicial.service.PerfilService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Andre Ribeiro on 09/06/2017.
 */
@Named
@ViewScoped
public class PerfilBean implements Serializable {

    private Perfil perfil;
    private final LoginBean loginBean;

    @Inject
    public PerfilBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        perfil = PerfilService.getPerfilById(loginBean.getPerfil().getId());
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        PerfilService.update(perfil);
        return "perfil?&faces-redirect=true";

    }

    public Perfil getPerfil() {return perfil;}

    public void setPerfil(Perfil perfil) {this.perfil = perfil;}

    public LoginBean getLoginBean() {return loginBean;}
}
