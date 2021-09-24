package br.com.devdojo.projetoinicial.persistence.model;

public class AbstractTipo extends AbstractEntity {

    protected String tipo;

    public AbstractTipo(String tipo) {
        this.tipo = tipo;
    }

    public AbstractTipo() {
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "AbstractTipo{" +
                "tipo='" + tipo + '\'' +
                "} " + super.toString();
    }
}
