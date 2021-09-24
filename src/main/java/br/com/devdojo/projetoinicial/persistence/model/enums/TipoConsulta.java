package br.com.devdojo.projetoinicial.persistence.model.enums;

/**
 * @author André Ribeiro, William Suane on 5/3/17.
 */
public enum TipoConsulta {
    NORMAL("Consulta Normal"), SESSAO("Consulta Sessão"), RETORNO("Consulta Retorno");
    private String label;

    TipoConsulta(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
