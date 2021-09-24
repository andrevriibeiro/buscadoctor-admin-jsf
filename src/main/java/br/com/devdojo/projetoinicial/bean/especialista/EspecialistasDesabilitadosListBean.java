package br.com.devdojo.projetoinicial.bean.especialista;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.service.consultorio.ConsultorioService;
import br.com.devdojo.projetoinicial.service.dataloader.EspecialistasConsultorioDataLoader;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andre Ribeiro on 07/11/17.
 */
@Named
@ViewScoped
public class EspecialistasDesabilitadosListBean implements Serializable {

    private final LoginBean loginBean;
    private List<EspecialistaConsultorio> especialistasConsultorioList;
    private Consultorio consultorio;
    private int qtdEspecialistaAvailableToRegister;
    private boolean permiteHabilitar;

    @Inject
    public EspecialistasDesabilitadosListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() {
        consultorio = ConsultorioService.getById(loginBean.getPerfil().getConsultorio().getId());
        loadEspecialistasEnabled();

        permiteHabilitar = true ? qtdEspecialistaAvailableToRegister != 0 : false;
        warningMsgNotAvailabletoRegister();
    }

    @Transactional
    public void enableEspecialistaConsultorio(EspecialistaConsultorio especialistaConsultorio) {
        if(qtdEspecialistaAvailableToRegister!=0){
            EspecialistaConsultorioService.update(especialistaConsultorio);
            loadEspecialistasEnabled();
            qtdEspecialistaAvailableToRegister --;
        }else{
            warningMsgNotAvailabletoRegister();
        }
    }

    private void loadEspecialistasEnabled(){
        especialistasConsultorioList = EspecialistasConsultorioDataLoader.getEspecialistaConsultorioDisabled(consultorio)
                .stream()
                .sorted(Comparator.comparing(especialistaDTO -> especialistaDTO.getEspecialista().getNome()))
                .collect(Collectors.toList());
    }

    private void warningMsgNotAvailabletoRegister(){
        FacesUtils.addWarningMessage(qtdEspecialistaAvailableToRegister <= 0 ?
                "warningQtdIndisponivelParaHabilitarEspecialista" : "warningQtdIndisponivelParaHabilitarEspecialista", false);
    }

    public List<EspecialistaConsultorio> getEspecialistasConsultorioList() {return especialistasConsultorioList;}

    public void setEspecialistasConsultorioList(List<EspecialistaConsultorio> especialistasConsultorioList) {this.especialistasConsultorioList = especialistasConsultorioList;}

    public int getQtdEspecialistaAvailableToRegister() {return qtdEspecialistaAvailableToRegister;}

    public void setQtdEspecialistaAvailableToRegister(int qtdEspecialistaAvailableToRegister) {this.qtdEspecialistaAvailableToRegister = qtdEspecialistaAvailableToRegister;}

    public boolean isPermiteHabilitar() {return permiteHabilitar;}

    public void setPermiteHabilitar(boolean permiteHabilitar) {this.permiteHabilitar = permiteHabilitar;}
}
