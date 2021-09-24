package br.com.devdojo.projetoinicial.persistence.model;

public class EspecialistaConvenio extends AbstractEntity {

    private Especialista especialista;
    private Convenio convenio;
    private Boolean trabalhaferiado;

    public EspecialistaConvenio() {
    }

    private EspecialistaConvenio(Builder builder) {
        id = builder.id;
        especialista = builder.especialista;
        convenio = builder.convenio;
        trabalhaferiado = builder.trabalhaferiado;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public Boolean getTrabalhaferiado() {
        return trabalhaferiado;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private Integer id;
        private Especialista especialista;
        private Convenio convenio;
        private Boolean trabalhaferiado;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder especialista(Especialista especialista) {
            this.especialista = especialista;
            return this;
        }

        public Builder convenio(Convenio convenio) {
            this.convenio = convenio;
            return this;
        }

        public Builder trabalhaferiado(Boolean trabalhaferiado) {
            this.trabalhaferiado = trabalhaferiado;
            return this;
        }

        public EspecialistaConvenio build() {
            return new EspecialistaConvenio(this);
        }
    }
}