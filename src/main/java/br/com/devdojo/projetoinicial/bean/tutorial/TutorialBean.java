package br.com.devdojo.projetoinicial.bean.tutorial;

import br.com.devdojo.projetoinicial.persistence.model.Tutorial;
import br.com.devdojo.projetoinicial.persistence.model.enums.TutorialEnum;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andre Ribeiro on 24/06/17.
 */
@Named
@ViewScoped
public class TutorialBean implements Serializable {

    private ArrayList<Tutorial> tutorial;

    public void init() {
        biuldTutorial();
    }

    public void biuldTutorial() {
        tutorial = new ArrayList<>();

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ALTERAR_SENHA_USUARIO.getDescricao()).grupo(TutorialEnum.ALTERAR_SENHA_USUARIO.getGrupo()).path(TutorialEnum.ALTERAR_SENHA_USUARIO.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ADICIONAR_USUARIO.getDescricao()).grupo(TutorialEnum.ADICIONAR_USUARIO.getGrupo()).path(TutorialEnum.ADICIONAR_USUARIO.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.EDITAR_USUARIO.getDescricao()).grupo(TutorialEnum.EDITAR_USUARIO.getGrupo()).path(TutorialEnum.EDITAR_USUARIO.getPath()).build());

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.CADASTRAR_ESPECIALISTA.getDescricao()).grupo(TutorialEnum.CADASTRAR_ESPECIALISTA.getGrupo()).path(TutorialEnum.CADASTRAR_ESPECIALISTA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.EDITAR_ESPECIALISTA.getDescricao()).grupo(TutorialEnum.EDITAR_ESPECIALISTA.getGrupo()).path(TutorialEnum.EDITAR_ESPECIALISTA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ADICIONAR_CONVENIOS_ESPECIALISTA.getDescricao()).grupo(TutorialEnum.ADICIONAR_CONVENIOS_ESPECIALISTA.getGrupo()).path(TutorialEnum.ADICIONAR_CONVENIOS_ESPECIALISTA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ADICIONAR_PROCEDIMENTOS_ESPECIALISTA.getDescricao()).grupo(TutorialEnum.ADICIONAR_PROCEDIMENTOS_ESPECIALISTA.getGrupo()).path(TutorialEnum.ADICIONAR_PROCEDIMENTOS_ESPECIALISTA.getPath()).build());

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.CADASTRAR_AGENDA_DIARIA.getDescricao()).grupo(TutorialEnum.CADASTRAR_AGENDA_DIARIA.getGrupo()).path(TutorialEnum.CADASTRAR_AGENDA_DIARIA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.CADASTRAR_AGENDA_MENSAL.getDescricao()).grupo(TutorialEnum.CADASTRAR_AGENDA_MENSAL.getGrupo()).path(TutorialEnum.CADASTRAR_AGENDA_MENSAL.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.EDITAR_AGENDA_DIARIA.getDescricao()).grupo(TutorialEnum.EDITAR_AGENDA_DIARIA.getGrupo()).path(TutorialEnum.EDITAR_AGENDA_DIARIA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.EDITAR_AGENDA_MENSAL.getDescricao()).grupo(TutorialEnum.EDITAR_AGENDA_MENSAL.getGrupo()).path(TutorialEnum.EDITAR_AGENDA_MENSAL.getPath()).build());

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.AGENDAR_CONSULTA.getDescricao()).grupo(TutorialEnum.AGENDAR_CONSULTA.getGrupo()).path(TutorialEnum.AGENDAR_CONSULTA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.AGENDAR_SESSAO.getDescricao()).grupo(TutorialEnum.AGENDAR_SESSAO.getGrupo()).path(TutorialEnum.AGENDAR_SESSAO.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.REAGENDAR_CONSULTA.getDescricao()).grupo(TutorialEnum.REAGENDAR_CONSULTA.getGrupo()).path(TutorialEnum.REAGENDAR_CONSULTA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.CANCELAR_CONSULTA.getDescricao()).grupo(TutorialEnum.CANCELAR_CONSULTA.getGrupo()).path(TutorialEnum.CANCELAR_CONSULTA.getPath()).build());

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.SEARCH_CONSULTA_DIA.getDescricao()).grupo(TutorialEnum.SEARCH_CONSULTA_DIA.getGrupo()).path(TutorialEnum.SEARCH_CONSULTA_DIA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.SEARCH_TODAS_CONSULTAS.getDescricao()).grupo(TutorialEnum.SEARCH_TODAS_CONSULTAS.getGrupo()).path(TutorialEnum.SEARCH_TODAS_CONSULTAS.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ALTERANDO_STATUS_CONSULTA.getDescricao()).grupo(TutorialEnum.ALTERANDO_STATUS_CONSULTA.getGrupo()).path(TutorialEnum.ALTERANDO_STATUS_CONSULTA.getPath()).build());

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.CADASTRAR_PACIENTE.getDescricao()).grupo(TutorialEnum.CADASTRAR_PACIENTE.getGrupo()).path(TutorialEnum.CADASTRAR_PACIENTE.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.PESQUISANDO_PACIENTE.getDescricao()).grupo(TutorialEnum.PESQUISANDO_PACIENTE.getGrupo()).path(TutorialEnum.PESQUISANDO_PACIENTE.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.EDITANDO_PACIENTE.getDescricao()).grupo(TutorialEnum.EDITANDO_PACIENTE.getGrupo()).path(TutorialEnum.EDITANDO_PACIENTE.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.IMPRIMINDO_PRONTUARIO.getDescricao()).grupo(TutorialEnum.IMPRIMINDO_PRONTUARIO.getGrupo()).path(TutorialEnum.IMPRIMINDO_PRONTUARIO.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.CONSULTA_PACIENTE.getDescricao()).grupo(TutorialEnum.CONSULTA_PACIENTE.getGrupo()).path(TutorialEnum.CONSULTA_PACIENTE.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ATUALIZANDO_CONSULTA_PACIENTE.getDescricao()).grupo(TutorialEnum.ATUALIZANDO_CONSULTA_PACIENTE.getGrupo()).path(TutorialEnum.ATUALIZANDO_CONSULTA_PACIENTE.getPath()).build());

        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.EDITAR_DADOS_CLINICA.getDescricao()).grupo(TutorialEnum.EDITAR_DADOS_CLINICA.getGrupo()).path(TutorialEnum.EDITAR_DADOS_CLINICA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ADICIONAR_CONVENIO_CLINICA.getDescricao()).grupo(TutorialEnum.ADICIONAR_CONVENIO_CLINICA.getGrupo()).path(TutorialEnum.ADICIONAR_CONVENIO_CLINICA.getPath()).build());
        tutorial.add(Tutorial.newBuilder().descricao(TutorialEnum.ADICIONAR_PROCEDIMENTOS_CLINICA.getDescricao()).grupo(TutorialEnum.ADICIONAR_PROCEDIMENTOS_CLINICA.getGrupo()).path(TutorialEnum.ADICIONAR_PROCEDIMENTOS_CLINICA.getPath()).build());
    }

    public ArrayList<Tutorial> getTutorial() {return tutorial;}

    public void setTutorial(ArrayList<Tutorial> tutorial) {this.tutorial = tutorial;}
}
