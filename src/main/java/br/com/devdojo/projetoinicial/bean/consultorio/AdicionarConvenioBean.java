package br.com.devdojo.projetoinicial.bean.consultorio;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.ConsultorioConvenio;
import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.service.convenio.ConvenioService;
import br.com.devdojo.projetoinicial.service.convenioconsultorio.ConvenioConsultorioService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andre Ribeiro on 07/06/17.
 */
@Named
@ViewScoped
public class AdicionarConvenioBean implements Serializable{

    private final LoginBean loginBean;
    private List<ConsultorioConvenio> consultorioConvenios;
    private List<ConsultorioConvenio> consultorioConveniosToAdd = new ArrayList<>();
    private List<Convenio> convenioSelectedToAdd = new ArrayList<>();
    private List<Convenio> convenioList;

    public void init() {
        consultorioConvenios = ConvenioConsultorioService.getConveniosConsultorio(loginBean.getPerfil().getConsultorio().getId());
        convenioList = ConvenioService.getAllConvenios();
        convenioList.removeAll(consultorioConvenios);

        convenioList.removeAll(consultorioConvenios.stream().map(ConsultorioConvenio::getConvenio).collect(Collectors.toList()));
    }

    @Inject
    public AdicionarConvenioBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @Transactional
    public String adicionarConveniosConsultorio(){
        if(convenioSelectedToAdd.size()>0){
            convenioSelectedToAdd.forEach(convenio -> consultorioConveniosToAdd.add(ConsultorioConvenio.newBuilder()
                    .id(0)
                    .convenio(convenio)
                    .consultorio(loginBean.getPerfil().getConsultorio())
                    .build()));
            ConvenioConsultorioService.update(consultorioConveniosToAdd);

            return "convenio?faces-redirect=true";

        }else{
            FacesUtils.addWarningMessage(convenioSelectedToAdd.size()== 0 ?
                    "warningNenhumConvenioSelecionado" : "warningNenhumConvenioSelecionado", false);

            return null;
        }

    }

    public LoginBean getLoginBean() {return loginBean;}

    public List<Convenio> getConvenioList() {return convenioList;}

    public void setConvenioList(List<Convenio> convenioList) {this.convenioList = convenioList;}

    public List<Convenio> getConvenioSelectedToAdd() {return convenioSelectedToAdd;}

    public void setConvenioSelectedToAdd(List<Convenio> convenioSelectedToAdd) {this.convenioSelectedToAdd = convenioSelectedToAdd;}
}
