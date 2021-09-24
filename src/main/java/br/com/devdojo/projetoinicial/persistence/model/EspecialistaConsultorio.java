package br.com.devdojo.projetoinicial.persistence.model;

public class EspecialistaConsultorio extends AbstractEntity {

    private Especialista especialista;
    private Consultorio consultorio;
    private Boolean trabalhaferiado;
    private Boolean habilitado;

    public EspecialistaConsultorio() {
    }

    private EspecialistaConsultorio(Builder builder) {
        id = builder.id;
        especialista = builder.especialista;
        consultorio = builder.consultorio;
        trabalhaferiado = builder.trabalhaferiado;
        habilitado = builder.habilitado;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Boolean getTrabalhaferiado() {
        return trabalhaferiado;
    }

    public void setTrabalhaferiado(Boolean trabalhaferiado) {
        this.trabalhaferiado = trabalhaferiado;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private Especialista especialista;
        private Consultorio consultorio;
        private Boolean trabalhaferiado;
        private Boolean habilitado;

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

        public Builder consultorio(Consultorio consultorio) {
            this.consultorio = consultorio;
            return this;
        }

        public Builder trabalhaferiado(Boolean trabalhaferiado) {
            this.trabalhaferiado = trabalhaferiado;
            return this;
        }

        public Builder habilitado(Boolean habilitado) {
            this.habilitado = habilitado;
            return this;
        }

        public EspecialistaConsultorio build() {
            return new EspecialistaConsultorio(this);
        }
    }
}