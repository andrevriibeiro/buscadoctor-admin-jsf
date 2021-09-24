package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>, Andre Ribeiro <andreri.up@gmail.com>
 */
public class Logradouro extends AbstractEntity {

    private String nome;
    private String tipo;
    private String bairro;
    private String cep;
    private Cidade cidade;

    public Logradouro() {
    }

    public Logradouro(int id) {
        this.setId(id);
    }

    private Logradouro(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        tipo = builder.tipo;
        bairro = builder.bairro;
        cep = builder.cep;
        cidade = builder.cidade;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public static final class Builder {

        private Integer id;
        private String nome;
        private String tipo;
        private String bairro;
        private String cep;
        private Cidade cidade;

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

        public Builder tipo(String val) {
            tipo = val;
            return this;
        }

        public Builder bairro(String val) {
            bairro = val;
            return this;
        }

        public Builder cep(String val) {
            cep = val;
            return this;
        }

        public Builder cidade(Cidade val) {
            cidade = val;
            return this;
        }

        public Logradouro build() {
            return new Logradouro(this);
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Logradouro{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
               // ", cidade='" + cidade + '\'' +
                "} " + super.toString();
    }
}
