package br.com.devdojo.projetoinicial.persistence.model.dto;

import br.com.devdojo.projetoinicial.persistence.model.*;

import java.util.List;

/**
 * @author André Ribeiro, William Suane on 5/4/17.
 */
public class ConsultaDTO extends AbstractEntity {
    private List<Consulta> consultas;
    private Paciente paciente;
    private Convenio convenio;
    private TipoProcedimento tipoProcedimento;
    private ProcedimentoEspecialista procedimentoEspecialista;
    private boolean unicaGuiaSessao;
    private boolean consultaRetorno;
//    private boolean novoUsuario;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

//    public boolean isNovoUsuario() {
//        return novoUsuario;
//    }
//
//    public void setNovoUsuario(boolean novoUsuario) {
//        this.novoUsuario = novoUsuario;
//    }

    public boolean isConsultaRetorno() {
        return consultaRetorno;
    }

    public void setConsultaRetorno(boolean consultaRetorno) {
        this.consultaRetorno = consultaRetorno;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public TipoProcedimento getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public ProcedimentoEspecialista getProcedimentoEspecialista() {
        return procedimentoEspecialista;
    }

    public void setProcedimentoEspecialista(ProcedimentoEspecialista procedimentoEspecialista) {
        this.procedimentoEspecialista = procedimentoEspecialista;
    }

    public boolean isUnicaGuiaSessao() {
        return unicaGuiaSessao;
    }

    public void setUnicaGuiaSessao(boolean unicaGuiaSessao) {
        this.unicaGuiaSessao = unicaGuiaSessao;
    }
}
