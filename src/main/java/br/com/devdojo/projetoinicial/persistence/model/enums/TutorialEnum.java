package br.com.devdojo.projetoinicial.persistence.model.enums;

/**
 * Created by Andre Ribeiro on 27/06/2017.
 */
public enum TutorialEnum {

    /** CONFIGURAÇÃO DO SISTEMA **/
    ALTERAR_SENHA_USUARIO("Como alterar sua senha de acesso no sistema?","CONFIGURAÇÃO DO SISTEMA","/files/guia_completo.pdf"),
    ADICIONAR_USUARIO("Como adicionar novos usuários no sistema?","CONFIGURAÇÃO DO SISTEMA","/files/guia_completo.pdf"),
    EDITAR_USUARIO("Como editar usários do sistema?","CONFIGURAÇÃO DO SISTEMA","/files/guia_completo.pdf"),

    /** ESPECIALISTA **/
    CADASTRAR_ESPECIALISTA("Como cadastrar um novo especialista no sistema?","CONFIGURAÇÃO DO ESPECIALISTA","/files/guia_completo.pdf"),
    EDITAR_ESPECIALISTA("Como editar um especialista já cadastrado no sistema?","CONFIGURAÇÃO DO ESPECIALISTA","/files/guia_completo.pdf"),
    ADICIONAR_CONVENIOS_ESPECIALISTA("Como adicionar convênios aceitos pelo especialista?","CONFIGURAÇÃO DO ESPECIALISTA","/files/guia_completo.pdf"),
    ADICIONAR_PROCEDIMENTOS_ESPECIALISTA("Como adicionar procedimentos aceitos pelo especialista?","CONFIGURAÇÃO DO ESPECIALISTA","/files/guia_completo.pdf"),

    /** AGENDA **/
    CADASTRAR_AGENDA_DIARIA("Como cadastrar uma agenda diaria para o especialista?","CONFIGURANDO UMA AGENDA","/files/guia_completo.pdf"),
    CADASTRAR_AGENDA_MENSAL("Como cadastrar uma agenda mensal para o especialista?","CONFIGURANDO UMA AGENDA","/files/guia_completo.pdf"),
    EDITAR_AGENDA_DIARIA("Como editar uma agenda diaria do especialista?","CONFIGURANDO UMA AGENDA","/files/guia_completo.pdf"),
    EDITAR_AGENDA_MENSAL("Como editar uma agenda mensal do especialista?","CONFIGURANDO UMA AGENDA","/files/guia_completo.pdf"),

    /** AGENDAMENTO DE CONSULTA **/
    AGENDAR_CONSULTA("Como agendar uma consulta no sistema?","AGENDAMENTO DE CONSULTA","/files/guia_completo.pdf"),
    AGENDAR_SESSAO("Como agendar uma sessão no sistema?","AGENDAMENTO DE CONSULTA","/files/guia_completo.pdf"),
    REAGENDAR_CONSULTA("Como reagendar uma consulta no sistema?","AGENDAMENTO DE CONSULTA","/files/guia_completo.pdf"),
    CANCELAR_CONSULTA("Como cancelar uma consulta no sistema","AGENDAMENTO DE CONSULTA","/files/guia_completo.pdf"),

    /** PRÓXIMAS CONSULTA **/
    SEARCH_CONSULTA_DIA("Como visualizar as consultas do dia no sistema?","PESQUISANDO E IMPRIMINDO CONSULTA","/files/guia_completo.pdf"),
    SEARCH_TODAS_CONSULTAS("Como visualizar próximas consultas (consultas de outros dias) no sistema","PESQUISANDO E IMPRIMINDO CONSULTA","/files/guia_completo.pdf"),
    ALTERANDO_STATUS_CONSULTA("Como alterar o status de uma consulta no sistema","PESQUISANDO E IMPRIMINDO CONSULTA","/files/guia_completo.pdf"),

    /** PACIENTE **/
    CADASTRAR_PACIENTE("Como cadastrar um novo paciente da clínica no sistema?","PERFIL DO PACIENTE","/files/guia_completo.pdf"),
    PESQUISANDO_PACIENTE("Como encontrar pacientes da clínica cadastrados no sistema?","PERFIL DO PACIENTE","/files/guia_completo.pdf"),
    EDITANDO_PACIENTE("Como editar dados do paciente da clínica no sistema?","PERFIL DO PACIENTE","/files/guia_completo.pdf"),
    IMPRIMINDO_PRONTUARIO("Como imprimir prontuários do paciente no sistema?","PERFIL DO PACIENTE","/files/guia_completo.pdf"),
    CONSULTA_PACIENTE("Como visualizar consultas do pacientes no sistema?","PERFIL DO PACIENTE","/files/guia_completo.pdf"),
    ATUALIZANDO_CONSULTA_PACIENTE("Como atualizar informações da consulta do paciente no sistema?","PERFIL DO PACIENTE","/files/guia_completo.pdf"),

    /** PERFIL DO CONSULTÓRIO **/
    EDITAR_DADOS_CLINICA("Como editar dados da clínica?","CONFIGURAÇÃO DA CLÍNICA","/files/guia_completo.pdf"),
    ADICIONAR_CONVENIO_CLINICA("Como adicionar convênios aceitos pela clínica?","CONFIGURAÇÃO DA CLÍNICA","/files/guia_completo.pdf"),
    ADICIONAR_PROCEDIMENTOS_CLINICA("Como adicionar procedimentos aceitos pela clínica?","CONFIGURAÇÃO DA CLÍNICA","/files/guia_completo.pdf");

    private String descricao;
    private String grupo;
    private String path;

    TutorialEnum(String descricao, String grupo, String path){
        this.descricao = descricao;
        this.grupo = grupo;
        this.path = path;
    }

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getGrupo() {return grupo;}

    public void setGrupo(String grupo) {this.grupo = grupo;}

    public String getPath() {return path;}

    public void setPath(String path) {this.path = path;}
}
