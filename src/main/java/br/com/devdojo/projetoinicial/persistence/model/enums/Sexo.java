package br.com.devdojo.projetoinicial.persistence.model.enums;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public enum Sexo {
    MASCULINO("Masculino"), FEMININO("Feminino");
    private String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
