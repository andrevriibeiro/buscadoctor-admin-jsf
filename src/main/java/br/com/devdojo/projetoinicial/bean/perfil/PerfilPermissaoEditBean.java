package br.com.devdojo.projetoinicial.bean.perfil;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Perfil;
import br.com.devdojo.projetoinicial.persistence.model.Permissao;
import br.com.devdojo.projetoinicial.service.PerfilService;
import br.com.devdojo.projetoinicial.service.permissao.PermissaoService;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */

@Named
@ViewScoped
public class PerfilPermissaoEditBean implements Serializable {
    private int perfilId;
    private Perfil perfil;
    private List<SelectItem> permissaoAgrupadaList = new ArrayList<>(36);
    private final LoginBean loginBean;

    @Inject
    public PerfilPermissaoEditBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        perfil = PerfilService.getPerfilById(perfilId);
        if (perfil.getPermissao() == null || perfil.getPermissao().isEmpty())
            perfil.setPermissao(new LinkedHashSet<>());
        groupPermissoesByNomeDaTela();
    }

    private void groupPermissoesByNomeDaTela() {
        Map<String, List<Permissao>> telaPermissaoMap = PermissaoService.getAllPermissoes().stream().collect(Collectors.groupingBy(Permissao::getTela, TreeMap::new, Collectors.toList()));

        for (Map.Entry<String, List<Permissao>> entry : telaPermissaoMap.entrySet()) {
            SelectItemGroup tituloDoAgrupamentoDosCheckboxes = new SelectItemGroup(entry.getKey());
            List<SelectItem> permissoesCheckBox = new ArrayList<>(entry.getValue().size());
            for (Permissao permissao : entry.getValue()) {
                permissoesCheckBox.add(new SelectItem(permissao, permissao.getDescricao()));
            }
            tituloDoAgrupamentoDosCheckboxes.setSelectItems(permissoesCheckBox.toArray(new SelectItem[permissoesCheckBox.size()]));
            permissaoAgrupadaList.add(tituloDoAgrupamentoDosCheckboxes);
        }
    }

    @Transactional
    public String update() throws InterruptedException, ExecutionException, TimeoutException {
        PerfilService.update(perfil);
        return "usuarios?i=1&faces-redirect=true";
    }

    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<SelectItem> getPermissaoAgrupadaList() {
        return permissaoAgrupadaList;
    }

    public void setPermissaoAgrupadaList(List<SelectItem> permissaoAgrupadaList) {
        this.permissaoAgrupadaList = permissaoAgrupadaList;
    }
}
