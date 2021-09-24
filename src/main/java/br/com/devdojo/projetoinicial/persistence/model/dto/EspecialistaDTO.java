package br.com.devdojo.projetoinicial.persistence.model.dto;

import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.Especialidade;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andre Ribeiro on 14/11/17.
 */
public class EspecialistaDTO implements Serializable {

    private Especialista especialista;
    private Consultorio consultorio;
    private List<Especialidade> especialidades;

    public EspecialistaDTO() {
    }

    public EspecialistaDTO(Builder builder) {
        this.especialista = builder.especialista;
        this.consultorio = builder.consultorio;
        this.especialidades = builder.especialidades;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newEspecialistaDTO() {return new Builder();}

    public Especialista getEspecialista() {return especialista;}

    public void setEspecialista(Especialista especialista) {this.especialista = especialista;}

    public Consultorio getConsultorio() {return consultorio;}

    public void setConsultorio(Consultorio consultorio) {this.consultorio = consultorio;}

    public List<Especialidade> getEspecialidades() {return especialidades;}

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public static final class Builder {

        private Especialista especialista;
        private Consultorio consultorio;
        private List<Especialidade> especialidades;


        private Builder() {}

        public Builder especialista(Especialista val) {
            especialista = val;
            return this;
        }

        public Builder consultorio(Consultorio val) {
            consultorio = val;
            return this;
        }

        public Builder especialidade(List<Especialidade> val){
            especialidades = val;
            return this;
        }

        public EspecialistaDTO build() {
            return new EspecialistaDTO(this);
        }
    }
}
