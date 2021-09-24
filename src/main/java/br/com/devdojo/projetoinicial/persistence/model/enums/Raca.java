package br.com.devdojo.projetoinicial.persistence.model.enums;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public enum Raca {
    BRANCA("Branca"), PRETA("Preta"), PARDA("Parda"), INDIGENA("Indígena"), AMARELA("Amarela"),
    NAO_DESEJO_DECLARAR("Não desejo declarar");
    private String descricao;

    Raca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
