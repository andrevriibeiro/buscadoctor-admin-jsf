package br.com.devdojo.projetoinicial.bean.especialista;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.service.consultorio.ConsultorioService;
import br.com.devdojo.projetoinicial.service.dataloader.EspecialistasConsultorioDataLoader;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Created by André Ribeiro, William Suane on 3/30/17.
 */
@Named
@ViewScoped
public class EspecialistasHabilitadosListBean implements Serializable {

    private final LoginBean loginBean;

    private List<EspecialistaConsultorio> especialistasConsultorioList;

    private Consultorio consultorio;

    private boolean permiteRegistrar;

    private int qtdEspecialistaAvailableToRegister;

    @Inject
    public EspecialistasHabilitadosListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init() throws InterruptedException, ExecutionException, TimeoutException {

        consultorio = ConsultorioService.getById(loginBean.getPerfil().getConsultorio().getId());

        loadEspecialistasEnabled();

        permiteRegistrar = checkAllowedRegister();
        qtdEspecialistaAvailableToRegister = calculateQtdEspecialistaAvailableToRegister();
    }

    private boolean checkAllowedRegister(){
        if(especialistasConsultorioList.size() < consultorio.getLimiteEspecialistas())
            return true;
        return false;
    }

    private void loadEspecialistasEnabled(){
        especialistasConsultorioList = EspecialistasConsultorioDataLoader.getEspecialistaConsultorioEnabled(consultorio)
                .stream()
                .sorted(Comparator.comparing(especialistaDTO -> especialistaDTO.getEspecialista().getNome()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void disableEspecialistaConsultorio(EspecialistaConsultorio especialistaConsultorio) throws InterruptedException, ExecutionException, TimeoutException {
        EspecialistaConsultorioService.update(especialistaConsultorio);
        loadEspecialistasEnabled();
        qtdEspecialistaAvailableToRegister = calculateQtdEspecialistaAvailableToRegister();
    }

    public int calculateQtdEspecialistaAvailableToRegister(){
        return consultorio.getLimiteEspecialistas() - especialistasConsultorioList.size();
    }

    public List<EspecialistaConsultorio> getEspecialistasConsultorioList() {return especialistasConsultorioList;}

    public void setEspecialistasConsultorioList(List<EspecialistaConsultorio> especialistaConsultoriosList) {this.especialistasConsultorioList = especialistaConsultoriosList;}

    public boolean isPermiteRegistrar() {return permiteRegistrar;}

    public void setPermiteRegistrar(boolean permiteRegistrar) {this.permiteRegistrar = permiteRegistrar;}

    public int getQtdEspecialistaAvailableToRegister() {return qtdEspecialistaAvailableToRegister;}

    public void setQtdEspecialistaAvailableToRegister(int qtdEspecialistaAvailableToRegister) {this.qtdEspecialistaAvailableToRegister = qtdEspecialistaAvailableToRegister;}
}
