package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuracao extends AbstractEntity {

    @JsonProperty("notificacaoconsulta")
    private Integer notificacaoConsulta;

    public Configuracao() {
    }

    public Configuracao(Integer notificacaoconsulta) {
        this.notificacaoConsulta = notificacaoconsulta;
    }

    public Integer getNotificacaoConsulta() {
        return notificacaoConsulta;
    }

    @Override
    public String toString() {
        return "Configuracao{" +
                "notificacaoConsulta=" + notificacaoConsulta +
                "} ";
    }
}
