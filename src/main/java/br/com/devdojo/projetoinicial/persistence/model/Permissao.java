package br.com.devdojo.projetoinicial.persistence.model;

public class Permissao extends AbstractEntity {

    private String nome;
    private String descricao;
    private String pagina;
    private String tela;

    public Permissao() {
    }

    private Permissao(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        descricao = builder.descricao;
        pagina = builder.pagina;
        tela = builder.tela;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private String descricao;
        private String pagina;
        private String tela;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder nome(String val) {
            nome = val;
            return this;
        }

        public Builder descricao(String val) {
            descricao = val;
            return this;
        }

        public Builder pagina(String pagina) {
            this.pagina = pagina;
            return this;
        }
        public Builder tela(String tela) {
            this.tela = tela;
            return this;
        }


        public Permissao build() {
            return new Permissao(this);
        }
    }

    @Override
    public String toString() {
        return "Permissao{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                "}";
    }
}
