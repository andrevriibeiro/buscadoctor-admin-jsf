package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @see 1.1.0
 * @since 1.0.0
 */
public class Tipo extends AbstractEntity {

    private String nome;

    public Tipo(String nome) {
        this.nome = nome;
    }

    public Tipo() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
