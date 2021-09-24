package br.com.devdojo.projetoinicial.bean.paciente;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.paciente.PacienteService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre Ribeiro on 18/04/17.
 */
@Named
@ViewScoped
public class ConsultaPacienteListBean implements Serializable {

    private final LoginBean loginBean;
    private int pacienteId;
    private String pacienteNome;
    private int usuarioId;
    private List<Consulta> consultas;
    private String status;

    public void init() {
        status = "pendente";
        searchConsultas();
    }

    @Inject
    public ConsultaPacienteListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    /**
     *
     * Esse metodo retorna as consultas do paciente baseado nas seguintes regras:
     *
     * Pendente: Uma consulta com status pendente será toda consulta que
     *         ainda não foi realizada ou que está sendo realizada.
     *         Sendo assim, as consultas com status: 3, 6, 7, 11 e 14
     *         são consideradas nesse metodo como uma CONSULTA PENDENTE.
     *
     * REALIZADA: Uma consulta com status realizado será toda a consulta que
     *          de fato já foi realizada. Sendo assim as consultas com status:
     *          8 são consideradas nesse metodo como uma CONSULTA REALIZADA.

     * CANCELADA: Uma consulta com statua cancelada será toda a consulta que
     *          foi cancelada ou o paciente não compareceu na consulta agendada
     *          Sendo assim, as consulta com status 5 e 9 são onsideradas nesse metodo
     *          como uma CONSULTA CANCELADA.
    * */
    public void searchConsultas(){

        if(status.equals("pendente")){
            consultas = PacienteService.getConsultasPacienteByConsultorio(
                    loginBean.getPerfil().getConsultorio().getId(),
                    usuarioId, getStatusConsultasPendente());
        }
        if(status.equals("realizada")){
            consultas = PacienteService.getConsultasPacienteByConsultorio(
                    loginBean.getPerfil().getConsultorio().getId(),
                    usuarioId, getStatusConsultasRealizadas());
        }
        if(status.equals("cancelada")){
            consultas = PacienteService.getConsultasPacienteByConsultorio(
                    loginBean.getPerfil().getConsultorio().getId(),
                    usuarioId, getStatusConsultasCanceladas());
        }
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}


    public List<Integer> getStatusConsultasPendente(){

        ArrayList<Integer> status = new ArrayList<>();
        status.add(StatusConsultaEnum.APROVADA.getId());
        status.add(StatusConsultaEnum.AGUARDANDO_ATENDIMENTO.getId());
        status.add(StatusConsultaEnum.EM_ATENDIMENTO.getId());
        status.add(StatusConsultaEnum.CONFIRMADO.getId());
        status.add(StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId());

        return status;
    }

    public List<Integer> getStatusConsultasRealizadas(){

        ArrayList<Integer> status = new ArrayList<>();
        status.add(StatusConsultaEnum.ATENDIDO.getId());

        return status;
    }

    public List<Integer> getStatusConsultasCanceladas(){

        ArrayList<Integer> status = new ArrayList<>();
        status.add(StatusConsultaEnum.CANCELADA.getId());
        status.add(StatusConsultaEnum.NAO_COMPARECEU.getId());

        return status;
    }
}
