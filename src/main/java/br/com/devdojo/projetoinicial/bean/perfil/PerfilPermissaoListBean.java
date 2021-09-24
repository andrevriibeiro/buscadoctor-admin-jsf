package br.com.devdojo.projetoinicial.bean.perfil;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Perfil;
import br.com.devdojo.projetoinicial.service.PerfilService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
@ViewScoped
public class PerfilPermissaoListBean implements Serializable {
    private List<Perfil> perfilList;
    private String nome;
    private final LoginBean loginBean;
    private int consultorioId;

    @PostConstruct
    public void init() {
        perfilList = getUsuariosClinica() ;
    }

    @Inject
    public PerfilPermissaoListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Perfil> getUsuariosClinica() {
        consultorioId = loginBean.getPerfil().getConsultorio().getId();
        return PerfilService.getUsuariosClinica(consultorioId);
    }

    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }


}
