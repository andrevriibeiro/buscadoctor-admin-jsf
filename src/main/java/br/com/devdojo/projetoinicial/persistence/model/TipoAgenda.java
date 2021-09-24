package br.com.devdojo.projetoinicial.persistence.model;

public class TipoAgenda extends AbstractTipo {

    private String tipo;
    private String configuracao;

    public TipoAgenda() {
    }

    private TipoAgenda(Builder builder) {
        id = builder.id;
        tipo = builder.tipo;
        configuracao = builder.configuracao;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTipo() { return tipo; }

    public String getConfiguracao() {
        return configuracao;
    }

    public static final class Builder {

        private Integer id;
        private String tipo;
        private String configuracao;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder tipo(String val) {
            tipo = val;
            return this;
        }

        public Builder configuracao(String val) {
            configuracao = val;
            return this;
        }

        public TipoAgenda build() {
            return new TipoAgenda(this);
        }
    }
}
