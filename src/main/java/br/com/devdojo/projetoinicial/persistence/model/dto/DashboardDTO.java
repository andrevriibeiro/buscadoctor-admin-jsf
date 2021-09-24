package br.com.devdojo.projetoinicial.persistence.model.dto;

import br.com.devdojo.projetoinicial.persistence.model.AbstractEntity;
import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;

import java.util.List;

/**
 * Created by Andre Ribeiro on 28/10/17.
 */
public class DashboardDTO extends AbstractEntity {

    private int qtdConsultasCanceladas;
    private int qtdConsultasAtendido;
    private int qtdConsultasDisponiveis;
    private int qtdConsultasConfirmadas;
    private int qtdConsultasPendenteAprovaçao;
    private int qtdConsultasNaoCompareceu;
    private int qtdConsultasPendenteCancelamento;
    private int qtdConsultasPendenterConfirmacao;
    private int qtdConsultasAgendadas;
    private int qtdConsultasRealizadas;
    private int qtdConsultasNaoConfirmadas;

    private List<Consulta> listaConsultas;
    private java.util.List<SearchDTO> listaHorarios;

    private float percentConsultasAgendadas;
    private float percentConsultasRealizadas;
    private float percentConsultasCanceladas;
    private float percentConsultasNaoConfirmada;

    public DashboardDTO(List<Consulta> listaConsultas, java.util.List<SearchDTO> listaHorarios) {
        this.listaConsultas = listaConsultas;
        this.listaHorarios = listaHorarios;
    }

    /**
     * Esse metodo eh responsavel por setar as quantidades de consultas por status
     *
     * */
    public void loadQtdConsultasByStatus(){

        this.qtdConsultasDisponiveis = listaHorarios.size();
        this.qtdConsultasConfirmadas = searchQtdConsultasByStatus(StatusConsultaEnum.CONFIRMADO.getId());
        this.qtdConsultasAgendadas = searchQtdConsultasByStatus(StatusConsultaEnum.APROVADA.getId());
        this.qtdConsultasRealizadas = searchQtdConsultasByStatus(StatusConsultaEnum.ATENDIDO.getId());
        this.qtdConsultasCanceladas = searchQtdConsultasByStatus(StatusConsultaEnum.CANCELADA.getId());
        this.qtdConsultasPendenteAprovaçao = searchQtdConsultasByStatus(StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId());
        this.qtdConsultasPendenteCancelamento = searchQtdConsultasByStatus(StatusConsultaEnum.PENDENTE_CANCELAMENTO.getId());
        this.qtdConsultasPendenterConfirmacao = searchQtdConsultasByStatus(StatusConsultaEnum.PENDENTE_CONFIRMACAO.getId());

        this.qtdConsultasAgendadas += qtdConsultasConfirmadas;

        this.qtdConsultasNaoConfirmadas = this.qtdConsultasAgendadas - this.qtdConsultasRealizadas - this.qtdConsultasCanceladas;
    }

    /**
     * Esse metodo eh responsavel por retornar a quantidade de consultas por status
     *
     * @param status das consultas
     * return quantidade de consultas
     *
     * */
    public int searchQtdConsultasByStatus(Integer status) {

        return (int) this.listaConsultas.stream().
                filter(consulta -> consulta.getStatus().getId().equals(status))
                .count();
    }

    /**
     * Esse metodo eh responsavel por carregar as porcentagens de consultas apresentadas
     * nos indicator cards do dashboard
     *
     * */
    private void loadPercentForCardIndicators(){
        if(this.qtdConsultasDisponiveis>0 && this.qtdConsultasAgendadas>0){
            this.percentConsultasAgendadas = (this.qtdConsultasAgendadas * 100) / this.qtdConsultasDisponiveis;
        }

        if(this.qtdConsultasCanceladas>0 && this.qtdConsultasAgendadas>0){
            this.percentConsultasCanceladas = (this.qtdConsultasCanceladas  * 100) / this.qtdConsultasAgendadas;
        }

        if(this.qtdConsultasNaoConfirmadas>0 && this.qtdConsultasAgendadas>0){
            this.percentConsultasNaoConfirmada = (this.qtdConsultasNaoConfirmadas * 100) / this.qtdConsultasAgendadas;
        }

        if(this.qtdConsultasRealizadas>0 && this.qtdConsultasAgendadas>0){
            this.percentConsultasRealizadas = (this.qtdConsultasRealizadas * 100) / this.qtdConsultasAgendadas;
        }
    }

    public List<Consulta> getListaConsulta() {return listaConsultas;}

    public void setListaConsulta(List<Consulta> listaConsulta) {this.listaConsultas = listaConsulta;}

    public java.util.List<SearchDTO> getListaHorarios() {return listaHorarios;}

    public void setListaHorarios(java.util.List<SearchDTO> listaHorarios) {this.listaHorarios = listaHorarios;}

    public float getPercentConsultasAgendadas() {return percentConsultasAgendadas;}

    public void setPercentConsultasAgendadas(float percentConsultasAgendadas) {this.percentConsultasAgendadas = percentConsultasAgendadas;}

    public float getPercentConsultasRealizadas() {return percentConsultasRealizadas;}

    public void setPercentConsultasRealizadas(float percentConsultasRealizadas) {this.percentConsultasRealizadas = percentConsultasRealizadas;}

    public float getPercentConsultasCanceladas() {return percentConsultasCanceladas;}

    public void setPercentConsultasCanceladas(float percentConsultasCanceladas) {this.percentConsultasCanceladas = percentConsultasCanceladas;}

    public float getPercentConsultasNaoConfirmada() {return percentConsultasNaoConfirmada;}

    public void setPercentConsultasNaoConfirmada(float percentConsultasNaoConfirmada) {this.percentConsultasNaoConfirmada = percentConsultasNaoConfirmada;}

    public int getQtdConsultasCanceladas() {return qtdConsultasCanceladas;}

    public void setQtdConsultasCanceladas(int qtdConsultasCanceladas) {this.qtdConsultasCanceladas = qtdConsultasCanceladas;}

    public int getQtdConsultasAtendido() {return qtdConsultasAtendido;}

    public void setQtdConsultasAtendido(int qtdConsultasAtendido) {this.qtdConsultasAtendido = qtdConsultasAtendido;}

    public int getQtdConsultasDisponiveis() {return qtdConsultasDisponiveis;}

    public void setQtdConsultasDisponiveis(int qtdConsultasDisponiveis) {this.qtdConsultasDisponiveis = qtdConsultasDisponiveis;}

    public int getQtdConsultasConfirmadas() {return qtdConsultasConfirmadas;}

    public void setQtdConsultasConfirmadas(int qtdConsultasConfirmadas) {this.qtdConsultasConfirmadas = qtdConsultasConfirmadas;}

    public int getQtdConsultasPendenteAprovaçao() {return qtdConsultasPendenteAprovaçao;}

    public void setQtdConsultasPendenteAprovaçao(int qtdConsultasPendenteAprovaçao) {this.qtdConsultasPendenteAprovaçao = qtdConsultasPendenteAprovaçao;}

    public int getQtdConsultasNaoCompareceu() {return qtdConsultasNaoCompareceu;}

    public void setQtdConsultasNaoCompareceu(int qtdConsultasNaoCompareceu) {this.qtdConsultasNaoCompareceu = qtdConsultasNaoCompareceu;}

    public int getQtdConsultasPendenteCancelamento() {return qtdConsultasPendenteCancelamento;}

    public void setQtdConsultasPendenteCancelamento(int qtdConsultasPendenteCancelamento) {this.qtdConsultasPendenteCancelamento = qtdConsultasPendenteCancelamento;}

    public int getQtdConsultasPendenterConfirmacao() {return qtdConsultasPendenterConfirmacao;}

    public void setQtdConsultasPendenterConfirmacao(int qtdConsultasPendenterConfirmacao) {this.qtdConsultasPendenterConfirmacao = qtdConsultasPendenterConfirmacao;}

    public int getQtdConsultasAgendadas() {return qtdConsultasAgendadas;}

    public void setQtdConsultasAgendadas(int qtdConsultasAgendadas) {this.qtdConsultasAgendadas = qtdConsultasAgendadas;}

    public int getQtdConsultasRealizadas() {return qtdConsultasRealizadas;}

    public void setQtdConsultasRealizadas(int qtdConsultasRealizadas) {this.qtdConsultasRealizadas = qtdConsultasRealizadas;}

    public int getQtdConsultasNaoConfirmadas() {return qtdConsultasNaoConfirmadas;}

    public void setQtdConsultasNaoConfirmadas(int qtdConsultasNaoConfirmadas) {this.qtdConsultasNaoConfirmadas = qtdConsultasNaoConfirmadas;}
}
