package br.com.devdojo.projetoinicial.persistence.model;

public class Especialidade extends AbstractEntity implements Comparable<Especialidade> {

    private String especialidade;
    private String CBOS;

    public Especialidade() {
    }

    public Especialidade(int id) {
        this.id = id;
    }

    private Especialidade(Builder builder) {
        setId(builder.id);
        especialidade = builder.especialidade;
        CBOS = builder.CBOS;
    }

    public static Especialidade.Builder newBuilder() {
        return new Especialidade.Builder();
    }

    public Especialidade(String especialidade, String CBOS) {
        this.especialidade = especialidade;
        this.CBOS = CBOS;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getCBOS() {
        return CBOS;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public int compareTo(Especialidade o) {
        if (this.especialidade != null && o.getEspecialidade() != null)
            return this.especialidade.compareTo(o.getEspecialidade());
        return 0;
    }

    public static final class Builder {
        private Integer id;
        private String especialidade;
        private String CBOS;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder especialidade(String val) {
            especialidade = val;
            return this;
        }

        public Builder CBOS(String val) {
            CBOS = val;
            return this;
        }

        public Especialidade build() {
            return new Especialidade(this);
        }
    }

    @Override
    public String toString() {
        return "Especialidade{" +
                "especialidade='" + especialidade + '\'' +
                ", CBOS='" + CBOS + '\'' +  ", id='" + id + '\'' +
                "} ";
    }
}
