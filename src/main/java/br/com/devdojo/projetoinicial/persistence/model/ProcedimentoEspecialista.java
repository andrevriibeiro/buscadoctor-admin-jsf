package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class ProcedimentoEspecialista extends AbstractEntity {

    private Especialista especialista;
    private ProcedimentoConvenio procedimentoConvenio;
    private Double repasse;
    private Double porcentagem;
    private Boolean liberado;

    public ProcedimentoEspecialista() {
    }

    private ProcedimentoEspecialista(Builder builder) {
        id = builder.id;
        especialista = builder.especialista;
        procedimentoConvenio = builder.procedimentoConvenio;
        repasse = builder.repasse;
        porcentagem = builder.porcentagem;
        liberado = builder.liberado;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public ProcedimentoConvenio getProcedimentoConvenio() {
        return procedimentoConvenio;
    }

    public Double getRepasse() {
        return repasse;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public static final class Builder {

        private Integer id;
        private Especialista especialista;
        private ProcedimentoConvenio procedimentoConvenio;
        private Double repasse;
        private Double porcentagem;
        private Boolean liberado;
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

        public Builder procedimentoConvenio(ProcedimentoConvenio procedimentoConvenio) {
            this.procedimentoConvenio = procedimentoConvenio;
            return this;
        }

        public Builder repasse(Double repasse) {
            this.repasse = repasse;
            return this;
        }

        public Builder porcentagem(Double porcentagem) {
            this.porcentagem = porcentagem;
            return this;
        }
        public Builder liberado(Boolean liberado) {
            this.liberado = liberado;
            return this;
        }
        public ProcedimentoEspecialista build() {
            return new ProcedimentoEspecialista(this);
        }
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public void setProcedimentoConvenio(ProcedimentoConvenio procedimentoConvenio) {
        this.procedimentoConvenio = procedimentoConvenio;
    }

    public void setRepasse(Double repasse) {
        this.repasse = repasse;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public Boolean getLiberado() {
        return liberado;
    }

    public void setLiberado(Boolean liberado) {
        this.liberado = liberado;
    }
}
