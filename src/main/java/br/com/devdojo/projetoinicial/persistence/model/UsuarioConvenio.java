package br.com.devdojo.projetoinicial.persistence.model;

/**
 * Created by Andre Ribeiro on 04/05/17.
 */
public class UsuarioConvenio extends AbstractEntity {

    private Convenio convenio;
    private Usuario usuario;
    private String tipo;
    private String numero;

    public UsuarioConvenio() {
    }

    private UsuarioConvenio(Builder builder) {
        setId(builder.id);
        convenio = builder.convenio;
        usuario = builder.usuario;
        tipo = builder.tipo;
        numero = builder.numero;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static final class Builder {
        private Integer id;
        private Convenio convenio;
        private Usuario usuario;
        private String tipo;
        private String numero;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder convenio(Convenio val) {
            convenio = val;
            return this;
        }

        public Builder usuario(Usuario val) {
            usuario = val;
            return this;
        }

        public Builder tipo(String val) {
            tipo = val;
            return this;
        }

        public Builder numero(String val) {
            numero = val;
            return this;
        }

        public UsuarioConvenio build() {
            return new UsuarioConvenio(this);
        }
    }
}
