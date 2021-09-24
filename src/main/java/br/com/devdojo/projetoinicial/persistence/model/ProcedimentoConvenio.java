package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.0.0
 * @since 1.0.0
 */
public class ProcedimentoConvenio extends AbstractEntity {

    private Procedimento procedimento;
    private Convenio convenio;
    private Consultorio consultorio;
    private Double valor;

    public ProcedimentoConvenio() {
    }

    private ProcedimentoConvenio(Builder builder) {
        id = builder.id;
        procedimento = builder.procedimento;
        convenio = builder.convenio;
        consultorio = builder.consultorio;
        valor = builder.valor;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public Double getValor() {
        return valor;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private Integer id;
        private Procedimento procedimento;
        private Convenio convenio;
        private Consultorio consultorio;
        private Double valor;

        public Builder() {
        }


        public Builder id(int val) {
            this.id = val;
            return this;
        }

        public Builder procedimento(Procedimento procedimento) {
            this.procedimento = procedimento;
            return this;
        }

        public Builder convenio(Convenio convenio) {
            this.convenio = convenio;
            return this;
        }

        public Builder consultorio(Consultorio consultorio) {
            this.consultorio = consultorio;
            return this;
        }

        public Builder valor(Double valor) {
            this.valor = valor;
            return this;
        }

        public ProcedimentoConvenio build() {
            return new ProcedimentoConvenio(this);
        }
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
