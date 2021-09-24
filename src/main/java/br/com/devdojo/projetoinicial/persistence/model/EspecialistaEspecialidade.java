package br.com.devdojo.projetoinicial.persistence.model;

import java.util.List;

/**
 * @author Andre Ribeiro on 3/30/2017.
 */
public class EspecialistaEspecialidade extends AbstractEntity {

    private Especialista especialista;
    private Especialidade especialidade;
    private boolean principal;

    public EspecialistaEspecialidade() {
    }

    private EspecialistaEspecialidade(Builder builder) {
        id = builder.id;
        especialista = builder.especialista;
        especialidade = builder.especialidade;
        principal = builder.principal;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public static class Builder {

        private Integer id;
        private Especialista especialista;
        private Especialidade especialidade;
        private List<Especialidade> especialidadeList;
        private boolean principal;

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

        public Builder especialidade(Especialidade especialidade) {
            this.especialidade = especialidade;
            return this;
        }

        public Builder principal(boolean principal) {
            this.principal = principal;
            return this;
        }

        public EspecialistaEspecialidade build() {
            return new EspecialistaEspecialidade(this);
        }
    }
}