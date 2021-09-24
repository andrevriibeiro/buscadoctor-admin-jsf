package br.com.devdojo.projetoinicial.persistence.model;

/**
 * Created by Andre Ribeiro on 24/06/17.
 */
public class Tutorial extends AbstractEntity {

    private String descricao;
    private String grupo;
    private String path;

    private Tutorial(Builder builder) {
        descricao = builder.descricao;
        grupo = builder.grupo;
        path = builder.path;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getGrupo() {return grupo;}

    public void setGrupo(String grupo) {this.grupo = grupo;}

    public String getPath() {return path;}

    public void setPath(String path) {this.path = path;}

    public static final class Builder {

        private String descricao;
        private String grupo;
        private String path;

        private Builder() {
        }

        public Builder descricao(String val) {
            descricao = val;
            return this;
        }

        public Builder path(String val) {
            path = val;
            return this;
        }

        public Builder grupo(String val){
            grupo = val;
            return this;
        }

        public Tutorial build() {
            return new Tutorial(this);
        }
    }
}

