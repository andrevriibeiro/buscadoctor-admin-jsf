package br.com.devdojo.projetoinicial.bean.paciente;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Paciente;
import br.com.devdojo.projetoinicial.persistence.model.Usuario;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.paciente.PacienteService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andre on 3/16/17.
 */
@Named
@ViewScoped
public class PacienteListBean implements Serializable {
    private List<Paciente> pacienteList;
    private String nome;
    private Consulta ultimaConsulta;
    private Paciente paciente;
    private final LoginBean loginBean;
    private List<Consulta> consultasPaciente;

    @PostConstruct
    public void init() {
        paciente = Paciente.newBuilder().usuario(new Usuario()).consultorio(loginBean.getPerfil().getConsultorio()).build();
    }

    @Inject
    public PacienteListBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void searchPacientes() throws ParseException {
        pacienteList = PacienteService.searchPacientes(paciente);
        searchConsultasPaciente(pacienteList);
    }

    // metodo de teste nao remover por enquanto
    public void searchConsultasPaciente(List<Paciente> pacientes){
        for(Paciente paciente: pacientes){
            consultasPaciente = PacienteService.getConsultasPacienteByConsultorio(
                    loginBean.getPerfil().getConsultorio().getId(),
                    paciente.getUsuario().getId(), getStatusConsultasRealizadas());

            paciente.setUltimaConsulta(consultasPaciente.get(consultasPaciente.size()-1));
        }
    }

    public List<Integer> getStatusConsultasRealizadas(){

        ArrayList<Integer> status = new ArrayList<>();
        status.add(StatusConsultaEnum.ATENDIDO.getId());

        return status;
    }


    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Paciente> getPacienteList() {
        return pacienteList;
    }

    public void setPacienteList(List<Paciente> pacienteList) {
        this.pacienteList = pacienteList;
    }

    public String getNome() {
        return nome;
    }


    public Consulta getUltimaConsulta() {
        return ultimaConsulta;
    }

    public void setUltimaConsulta(Consulta ultimaConsulta) {
        this.ultimaConsulta = ultimaConsulta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
